import java.util.*;

class JobSearchEngine {

    /**
     * The compact constructor normalises null text to "" so nothing downstream
     * has to null-check. Without it a null title is silently indexable (tokenize
     * tolerates null) but blows up later in the comparator - and only when two
     * docs happen to tie on score AND timePosted, since the earlier comparisons
     * short-circuit. Fixing it here makes a null title unrepresentable instead.
     */
    record JobDoc(int id, String title, String desc, long timePosted) {
        JobDoc {
            if (title == null) {
                title = "";
            }
            if (desc == null) {
                desc = "";
            }
        }
    }

    /**
     * DISABLED for now - same reasoning as STOPWORDS below. Dropping 1-char
     * tokens would make "C++" (-> "c") and single-letter skills like R
     * unsearchable, and we can't rule those out of the test cases.
     * To re-enable: uncomment this and the check in addToken().
     */
    // static final int MIN_TOKEN_LENGTH = 2;

    /**
     * DISABLED for now - with unknown test cases we can't risk a query like
     * "the office" silently scoring nothing. Every word is indexed and matched.
     *
     * When enabled, this filters BOTH docs and queries. Doc-side filtering is
     * the important one: these are the highest-frequency words in any corpus, so
     * indexing them creates postings lists holding nearly every doc - and since
     * queries strip them too, those lists could never be looked up anyway.
     * To re-enable: uncomment this and the check in addToken().
     */
    // static final Set<String> STOPWORDS = Set.of(
    //     "an", "and", "are", "as", "at", "be", "but", "by", "for", "from",
    //     "has", "have", "in", "into", "is", "it", "its", "of", "on", "or",
    //     "that", "the", "their", "then", "there", "they", "this", "to", "was",
    //     "we", "were", "will", "with", "you", "your"
    // );

    /**
     * The single tokenizer used by BOTH indexing and querying - they must
     * normalize identically or a query could never match a doc that contains it.
     *
     * Returns a List, not a Set, so repeats survive: doc 5's three "Java"s are
     * what give it a higher title frequency. The query path dedupes separately.
     */
    static List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return tokens;
        }

        int n = text.length();
        int start = -1; // start of the token currently being scanned, -1 if none

        // <= n so the loop body runs once past the end, flushing a trailing token
        for (int i = 0; i <= n; i++) {
            boolean isWordChar = i < n && Character.isLetterOrDigit(text.charAt(i));

            if (isWordChar) {
                if (start < 0) {
                    start = i;
                }
            } else if (start >= 0) {
                addToken(tokens, text, start, i);
                start = -1;
            }
        }

        return tokens;
    }

    /** Lowercases the token. Both optional filters are currently disabled. */
    private static void addToken(List<String> tokens, String text, int start, int end) {
        // checked before substring() so short tokens would allocate nothing
        // if (end - start < MIN_TOKEN_LENGTH) return; // re-enable with the constant above

        String token = text.substring(start, end).toLowerCase();

        // if (STOPWORDS.contains(token)) return; // re-enable with STOPWORDS above

        tokens.add(token);
    }

    /**
     * How often one term occurs in one doc, split by field so the 2-vs-1
     * weighting can be applied at query time. Mutable (not a record) because
     * indexing increments these in place as it scans.
     */
    static final class Posting {
        int titleFreq;
        int descFreq;

        @Override
        public String toString() {
            return "(t=" + titleFreq + ",d=" + descFreq + ")";
        }
    }

    /** term -> docId -> how often it occurs there. The main search structure. */
    private final Map<String, Map<Integer, Posting>> invertedIndex = new HashMap<>();

    // A forward index (docId -> its terms) was considered here and dropped: it
    // would roughly double index memory, and the tombstone-then-compact removal
    // we chose never needs it (compaction already walks every postings list).
    // Only targeted immediate removal would justify bringing it back.

    /**
     * docId -> the doc itself, for tie-breaking on timePosted and for returning
     * readable results. Holds a reference to the caller's JobDoc, so at real
     * scale this keeps every desc string alive; a compact
     * (id, timePosted) record would be the memory-conscious version.
     */
    private final Map<Integer, JobDoc> docStore = new HashMap<>();

    /** Indexes every doc. Just repeated indexDoc calls - no bulk-only logic. */
    void buildIndex(List<JobDoc> docs) {
        for (JobDoc doc : docs) {
            indexDoc(doc);
        }
    }

    /**
     * Adds one doc to all three structures in a single pass. This doubles as the
     * incremental add-doc primitive for Phase B.
     */
    void indexDoc(JobDoc doc) {
        if (doc == null) {
            return;
        }

        // Re-indexing an existing id would double-count its frequencies. Proper
        // handling is remove-then-add, which arrives with removal in Phase B.
        if (docStore.containsKey(doc.id())) {
            return;
        }

        docStore.put(doc.id(), doc);

        for (String term : tokenize(doc.title())) {
            postingFor(term, doc.id()).titleFreq++;
        }

        for (String term : tokenize(doc.desc())) {
            postingFor(term, doc.id()).descFreq++;
        }
    }

    /** Fetches the (term, docId) posting, creating the term and posting if new. */
    private Posting postingFor(String term, int docId) {
        return invertedIndex
            .computeIfAbsent(term, t -> new HashMap<>())
            .computeIfAbsent(docId, d -> new Posting());
    }

    static final int TITLE_WEIGHT = 2;
    static final int DESC_WEIGHT = 1;

    /** One ranked hit. */
    record ScoredDoc(JobDoc doc, int score) {}

    /**
     * Orders WORST first, which is what a size-K min-heap needs: the head is the
     * candidate to evict. It is the exact inverse of the final ranking
     * (score desc, newest first, then title A-Z).
     *
     * The later comparisons are not cosmetic - without them, docs tying on
     * everything above would come out in HashMap order, so identical queries
     * could return differently ordered results.
     */
    private static final Comparator<ScoredDoc> WORST_FIRST = (a, b) -> {
        if (a.score() != b.score()) {
            return Integer.compare(a.score(), b.score()); // lowest score evicted first
        }
        if (a.doc().timePosted() != b.doc().timePosted()) {
            return Long.compare(a.doc().timePosted(), b.doc().timePosted()); // oldest evicted first
        }

        // Title A-Z, so the alphabetically LAST title is evicted first.
        // Case-insensitive: a plain compareTo would rank every capitalised title
        // above every lowercase one ('Z' < 'a' in ASCII).
        int byTitle = b.doc().title().compareToIgnoreCase(a.doc().title());
        if (byTitle != 0) {
            return byTitle;
        }

        // Last resort: identical score, time, and title still need a stable order.
        return Integer.compare(b.doc().id(), a.doc().id());
    };

    /**
     * Returns up to k docs matching the query, best first.
     *
     * Cost is O(P + M log k), where P is the total length of the postings lists
     * for the query terms and M is the number of docs that matched at least one
     * term - never O(N), because docs sharing no term are never even visited.
     */
    List<ScoredDoc> search(String query, int k) {
        if (k <= 0) {
            return List.of();
        }

        // Dedupe: a term repeated in the query must not score twice.
        Set<String> terms = new HashSet<>(tokenize(query));
        if (terms.isEmpty()) {
            return List.of(); // null, blank, or punctuation-only query
        }

        // Only docs sharing a term with the query are ever touched.
        Map<Integer, Integer> scores = new HashMap<>();
        for (String term : terms) {
            Map<Integer, Posting> postings = invertedIndex.get(term);
            if (postings == null) {
                continue; // term appears in no doc
            }
            for (Map.Entry<Integer, Posting> entry : postings.entrySet()) {
                scores.merge(entry.getKey(), scoreOf(entry.getValue()), Integer::sum);
            }
        }

        // Size-k min-heap: push everything, evict the worst whenever it overflows.
        // Never pre-sized from k, which the caller may set enormously large.
        PriorityQueue<ScoredDoc> heap = new PriorityQueue<>(WORST_FIRST);
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            heap.add(new ScoredDoc(docStore.get(entry.getKey()), entry.getValue()));
            if (heap.size() > k) {
                heap.poll();
            }
        }

        // Draining yields worst-first, so reverse for the final ranking.
        LinkedList<ScoredDoc> results = new LinkedList<>();
        while (!heap.isEmpty()) {
            results.addFirst(heap.poll());
        }
        return results;
    }

    /**
     * What one matched term contributes to one doc: presence-only, so a title
     * hit is worth 2 no matter how often the word repeats. This is what stops
     * doc 5's keyword-stuffed "Java Java Java Developer" from outranking
     * genuinely relevant postings.
     *
     * For frequency weighting instead:
     *   return TITLE_WEIGHT * p.titleFreq + DESC_WEIGHT * p.descFreq;
     */
    private static int scoreOf(Posting p) {
        return (p.titleFreq > 0 ? TITLE_WEIGHT : 0)
             + (p.descFreq > 0 ? DESC_WEIGHT : 0);
    }

    static List<JobDoc> buildDummyData() {
        return List.of(
            new JobDoc(1, "Java Backend Developer",
                "We need a backend developer skilled in Java and Spring Boot.", 5),

            new JobDoc(2, "Java Backend Engineer",
                "We need someone experienced in backend systems.", 10), // tie-break pair with doc 7 (same words, older)

            new JobDoc(3, "Senior Software Engineer - Node.js",
                "Experience with Node.js and JavaScript required.", 8), // punctuation: "Node.js"

            new JobDoc(4, "C++ Developer",
                "", 3), // empty description

            new JobDoc(5, "Java Java Java Developer",
                "Repeated word test for duplicate title term frequency.", 1), // duplicate word within title

            new JobDoc(6, "Data Scientist",
                "Python and machine learning experience needed.", 15), // no overlap with "java" queries

            new JobDoc(7, "Backend Engineer Java",
                "We need someone experienced in backend systems.", 25), // tie-break pair with doc 2 (same words, newer)

            new JobDoc(8, "Frontend Engineer",
                "React and JavaScript developer needed for frontend team.", 20), // shares "developer"/"engineer" with others

            // Docs 9 and 10 tie on score AND timePosted, so only the alphabetical
            // tiebreak separates them. Listed Python-first to prove the ordering
            // is not just insertion order.
            new JobDoc(9, "Python Developer", "", 30),
            new JobDoc(10, "Angular Developer", "", 30)
        );
    }

    public static void main(String[] args) {
        JobSearchEngine engine = new JobSearchEngine();
        engine.buildIndex(buildDummyData());

        run(engine, "Java Developer", 5);   // normal multi-term query
        run(engine, "JAVA developer", 5);   // different case, identical ranking
        run(engine, "java java", 5);        // repeated term must not double-score
        run(engine, "backend engineer", 5); // docs 2 and 7 tie -> newest first
        run(engine, "developer", 5);        // docs 9 and 10 tie on score AND time -> A-Z
        run(engine, "Node.js", 5);          // punctuation splits into node + js
        run(engine, "C++", 5);              // single-char token still matches doc 4
        run(engine, "java", 2);             // k smaller than the match count
        run(engine, "java", 100);           // k larger than the match count
        run(engine, "java", 0);             // k <= 0
        run(engine, "quantum blockchain", 5); // no matches
        run(engine, "", 5);                 // empty query
        run(engine, "   ", 5);              // blank query
        run(engine, null, 5);               // null query
    }

    private static void run(JobSearchEngine engine, String query, int k) {
        System.out.println("\nquery=\"" + query + "\" k=" + k);
        List<ScoredDoc> results = engine.search(query, k);
        if (results.isEmpty()) {
            System.out.println("  (no results)");
            return;
        }
        for (ScoredDoc r : results) {
            System.out.println("  score=" + r.score()
                + "  posted=" + r.doc().timePosted()
                + "  doc " + r.doc().id() + ": " + r.doc().title());
        }
    }
}
