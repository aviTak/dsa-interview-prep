import java.util.*;

/**
 * Tests for JobSearchEngine. No JUnit on the classpath, so this is a minimal
 * assertion harness. Run with:  java SearchTest.java
 * Exits non-zero if anything fails, so it works as a CI gate.
 */
class SearchTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        tokenizerTests();
        scoringTests();
        rankingTests();
        topKTests();
        queryEdgeTests();
        indexingTests();

        System.out.println("\n" + passed + " passed, " + failed + " failed");
        if (failed > 0) {
            System.exit(1);
        }
    }

    // ---------- tokenizer ----------

    static void tokenizerTests() {
        section("tokenizer");

        check("lowercases", JobSearchEngine.tokenize("Java DEVELOPER"),
            List.of("java", "developer"));
        check("splits on punctuation", JobSearchEngine.tokenize("Node.js"),
            List.of("node", "js"));
        check("splits on hyphen and collapses spaces", JobSearchEngine.tokenize("full-stack   dev"),
            List.of("full", "stack", "dev"));
        check("keeps repeats (needed for term frequency)", JobSearchEngine.tokenize("Java Java"),
            List.of("java", "java"));
        check("keeps digits", JobSearchEngine.tokenize("Python3 5 years"),
            List.of("python3", "5", "years"));
        check("keeps single chars (C++ stays findable)", JobSearchEngine.tokenize("C++"),
            List.of("c"));
        check("null is empty", JobSearchEngine.tokenize(null), List.of());
        check("empty is empty", JobSearchEngine.tokenize(""), List.of());
        check("blank is empty", JobSearchEngine.tokenize("   "), List.of());
        check("punctuation only is empty", JobSearchEngine.tokenize("!!! ---"), List.of());
    }

    // ---------- scoring ----------

    static void scoringTests() {
        section("scoring");

        JobSearchEngine e = engineOf(
            doc(1, "java", "unrelated text", 10),  // title only
            doc(2, "unrelated", "java here", 10),  // desc only
            doc(3, "java", "java here", 10));      // both

        check("title match scores 2", scoreFor(e, "java", 1), 2);
        check("desc match scores 1", scoreFor(e, "java", 2), 1);
        check("title + desc scores 3", scoreFor(e, "java", 3), 3);

        JobSearchEngine multi = engineOf(
            doc(1, "java developer", "", 10));
        check("scores sum across query terms", scoreFor(multi, "java developer", 1), 4);
        check("unmatched query terms add nothing", scoreFor(multi, "java kubernetes", 1), 2);

        JobSearchEngine repeats = engineOf(
            doc(1, "java java java", "", 10),
            doc(2, "java", "", 10));
        check("presence-only: repeats in title do not multiply",
            scoreFor(repeats, "java", 1), 2);
        check("stuffed doc scores same as single mention",
            scoreFor(repeats, "java", 1), scoreFor(repeats, "java", 2));

        JobSearchEngine dedupe = engineOf(doc(1, "java", "java", 10));
        check("repeated query term does not double-score",
            scoreFor(dedupe, "java java java", 1), scoreFor(dedupe, "java", 1));
    }

    // ---------- ranking and tie-breaks ----------

    static void rankingTests() {
        section("ranking");

        JobSearchEngine byScore = engineOf(
            doc(1, "unrelated", "java", 10),   // 1
            doc(2, "java", "java", 10),        // 3
            doc(3, "java", "unrelated", 10));  // 2
        check("higher score ranks first", ids(byScore.search("java", 10)),
            List.of(2, 3, 1));

        JobSearchEngine byTime = engineOf(
            doc(1, "java", "", 10),
            doc(2, "java", "", 30),
            doc(3, "java", "", 20));
        check("score tie -> newest first", ids(byTime.search("java", 10)),
            List.of(2, 3, 1));

        JobSearchEngine byTitle = engineOf(
            doc(1, "java zebra", "", 10),
            doc(2, "java apple", "", 10),
            doc(3, "java mango", "", 10));
        check("score+time tie -> title A-Z", ids(byTitle.search("java", 10)),
            List.of(2, 3, 1));

        JobSearchEngine caseTitle = engineOf(
            doc(1, "java Zebra", "", 10),
            doc(2, "java apple", "", 10));
        check("title order is case-insensitive", ids(caseTitle.search("java", 10)),
            List.of(2, 1));

        JobSearchEngine byId = engineOf(
            doc(7, "java", "", 10),
            doc(3, "java", "", 10));
        check("all tie -> lowest id first (deterministic)", ids(byId.search("java", 10)),
            List.of(3, 7));

        check("recency outranks alphabetical",
            ids(engineOf(
                doc(1, "java zebra", "", 99),
                doc(2, "java apple", "", 10)).search("java", 10)),
            List.of(1, 2));
    }

    // ---------- top-K ----------

    static void topKTests() {
        section("top-K");

        JobSearchEngine e = engineOf(
            doc(1, "java", "", 10),
            doc(2, "java", "", 20),
            doc(3, "java", "", 30),
            doc(4, "java", "", 40));

        check("k smaller than matches truncates", ids(e.search("java", 2)),
            List.of(4, 3));
        check("k equal to matches returns all", ids(e.search("java", 4)).size(), 4);
        check("k larger than matches returns all, no error",
            ids(e.search("java", 1000)).size(), 4);
        check("k = 1 returns only the best", ids(e.search("java", 1)),
            List.of(4));
        check("k = 0 returns empty", ids(e.search("java", 0)), List.of());
        check("negative k returns empty", ids(e.search("java", -5)), List.of());
        check("huge k does not blow up on presize",
            ids(e.search("java", Integer.MAX_VALUE)).size(), 4);
        check("truncation keeps the top scorers, not arbitrary ones",
            ids(engineOf(
                doc(1, "java", "java", 10),   // 3
                doc(2, "unrelated", "java", 90), // 1, but newest
                doc(3, "java", "", 50))       // 2
                .search("java", 2)),
            List.of(1, 3));
    }

    // ---------- query edge cases ----------

    static void queryEdgeTests() {
        section("query edge cases");

        JobSearchEngine e = engineOf(doc(1, "java developer", "spring", 10));

        check("null query", e.search(null, 5), List.of());
        check("empty query", e.search("", 5), List.of());
        check("blank query", e.search("   ", 5), List.of());
        check("punctuation-only query", e.search("!!!", 5), List.of());
        check("no matching term", e.search("kubernetes", 5), List.of());
        check("partial word does not match (whole-word only)",
            e.search("dev", 5), List.of());
        check("case-insensitive end to end",
            ids(e.search("JAVA DEVELOPER", 5)), ids(e.search("java developer", 5)));
        check("empty index returns empty",
            new JobSearchEngine().search("java", 5), List.of());
    }

    // ---------- indexing ----------

    static void indexingTests() {
        section("indexing");

        JobSearchEngine e = new JobSearchEngine();
        e.indexDoc(null);
        check("null doc is ignored, not fatal", e.search("java", 5), List.of());

        JobSearchEngine nulls = engineOf(
            doc(1, null, "java backend", 10),
            doc(2, null, "java backend", 10));
        check("null title is normalised, no NPE on tie-break",
            ids(nulls.search("java", 5)), List.of(1, 2));
        check("null desc is normalised",
            engineOf(doc(1, "java", null, 10)).search("java", 5).size(), 1);

        check("empty desc indexes fine",
            scoreFor(engineOf(doc(1, "java", "", 10)), "java", 1), 2);

        JobSearchEngine dup = engineOf(
            doc(1, "java developer", "spring", 10),
            doc(1, "python developer", "django", 99));
        check("KNOWN GAP: re-indexing an id silently keeps the OLD doc",
            ids(dup.search("java", 5)), List.of(1));
        check("KNOWN GAP: the updated content is never indexed",
            dup.search("python", 5), List.of());

        check("buildIndex indexes every doc",
            JobSearchEngine.buildDummyData().size(), 10);
    }

    // ---------- harness ----------

    static void section(String name) {
        System.out.println("\n[" + name + "]");
    }

    static void check(String name, Object actual, Object expected) {
        if (Objects.equals(expected, actual)) {
            passed++;
            System.out.println("  PASS  " + name);
        } else {
            failed++;
            System.out.println("  FAIL  " + name);
            System.out.println("        expected: " + expected);
            System.out.println("        actual:   " + actual);
        }
    }

    /** Shorthand so tests read cleanly despite the nested type name. */
    static JobSearchEngine.JobDoc doc(int id, String title, String desc, long timePosted) {
        return new JobSearchEngine.JobDoc(id, title, desc, timePosted);
    }

    static JobSearchEngine engineOf(JobSearchEngine.JobDoc... docs) {
        JobSearchEngine engine = new JobSearchEngine();
        for (JobSearchEngine.JobDoc doc : docs) {
            engine.indexDoc(doc);
        }
        return engine;
    }

    /** Result doc ids in rank order. */
    static List<Integer> ids(List<JobSearchEngine.ScoredDoc> results) {
        List<Integer> out = new ArrayList<>();
        for (JobSearchEngine.ScoredDoc r : results) {
            out.add(r.doc().id());
        }
        return out;
    }

    /** Score the given doc received for a query, or -1 if it did not match. */
    static int scoreFor(JobSearchEngine engine, String query, int docId) {
        for (JobSearchEngine.ScoredDoc r : engine.search(query, Integer.MAX_VALUE)) {
            if (r.doc().id() == docId) {
                return r.score();
            }
        }
        return -1;
    }
}
