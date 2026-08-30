import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DocumentMatchingIndexer {

    // duplicate-name check; new ids are derived from idToName.size(), so no
    // name -> id lookup is ever needed
    private final Set<String> existingNames;

    // id -> name (index = id), used to resolve names during tie-break comparisons
    private final ArrayList<String> idToName;

    // token -> (docId -> term frequency of that token in that doc)
    private final Map<String, Map<Integer, Integer>> invertedIndex;

    public DocumentMatchingIndexer() {
        this.existingNames = new HashSet<>();
        this.idToName = new ArrayList<>();
        this.invertedIndex = new HashMap<>();
    }

    // Shared by addDocument and search so both paths tokenize identically.
    // Single pass, no regex (String.split(regex) recompiles a Pattern on
    // every call, which is wasteful at millions-of-calls scale). Lowercases
    // as it goes and treats any run of non-letter-or-digit characters as a
    // boundary; Character.isLetterOrDigit is Unicode-aware, so non-ASCII
    // letters are kept as part of tokens instead of being dropped.
    // Null/empty/all-special-char input simply yields an empty list - no error.
    private List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return tokens;
        }

        StringBuilder current = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                current.append(Character.toLowerCase(c));
            } else if (current.length() > 0) {
                tokens.add(current.toString());
                current.setLength(0);
            }
        }
        if (current.length() > 0) {
            tokens.add(current.toString());
        }
        return tokens;
    }

    public void addDocument(String name, String content) {
        if (name == null || name.trim().isEmpty() || existingNames.contains(name)) {
            return; // null/blank/duplicate name - silently skip
        }

        int id = idToName.size();
        existingNames.add(name);
        idToName.add(name);

        // null/empty content just yields no tokens - the doc is still
        // registered (so it occupies an id and can't be re-added later
        // under the same name), it just never matches any query.
        for (String token : tokenize(content)) {
            invertedIndex
                .computeIfAbsent(token, t -> new HashMap<>())
                .merge(id, 1, Integer::sum);
        }
    }

    public List<String> search(String query, int k) {
        if (query == null || query.isEmpty() || k <= 0) {
            return new ArrayList<>();
        }

        // dedupe query tokens so a repeated word doesn't inflate its own score
        Set<String> queryTokens = new HashSet<>(tokenize(query));
        if (queryTokens.isEmpty()) {
            return new ArrayList<>(); // query had no letters/digits at all (e.g. "!!! ---")
        }

        // docId -> accumulated score, built only from docs that actually
        // share a token with the query - never touches the full corpus
        Map<Integer, Integer> scores = new HashMap<>();
        for (String token : queryTokens) {
            Map<Integer, Integer> postings = invertedIndex.get(token);
            if (postings == null) {
                continue;
            }
            for (Map.Entry<Integer, Integer> posting : postings.entrySet()) {
                scores.merge(posting.getKey(), posting.getValue(), Integer::sum);
            }
        }

        // min-heap capped at size k: "smallest" = worst candidate, evicted
        // first when the heap grows past k. Worst = lowest score, and among
        // equal scores, the alphabetically later name (so the earlier name
        // survives).
        PriorityQueue<Integer> heap = new PriorityQueue<>((idA, idB) -> {
            int scoreA = scores.get(idA);
            int scoreB = scores.get(idB);
            if (scoreA != scoreB) {
                return Integer.compare(scoreA, scoreB);
            }
            return idToName.get(idB).compareTo(idToName.get(idA));
        });

        for (Integer id : scores.keySet()) {
            heap.offer(id);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        // drain the heap (worst-first) then reverse for score-desc/name-asc order
        List<String> result = new ArrayList<>(heap.size());
        while (!heap.isEmpty()) {
            result.add(idToName.get(heap.poll()));
        }
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        DocumentMatchingIndexer indexer = new DocumentMatchingIndexer();

        // normal docs - "java" repeated in JavaGuru should outweigh a single mention
        indexer.addDocument("JavaGuru", "Java Java Python Backend Engineer");
        indexer.addDocument("PySnake", "Python Data Science Engineer");
        indexer.addDocument("CppWizard", "C++ Systems co-founder don't 3.5 java8");

        // case-insensitivity: "ENGINEER" should still match doc content "engineer"
        indexer.addDocument("CaseTest", "ENGINEER");

        // tie-break check: same score (1 each, only "ruby" matches), alphabetical wins
        indexer.addDocument("Zeta", "Ruby");
        indexer.addDocument("Alpha", "Ruby");
        indexer.addDocument("Mango", "Ruby");

        // edge cases on addDocument
        indexer.addDocument("JavaGuru", "this duplicate name must be ignored");
        indexer.addDocument(null, "null name must be ignored");
        indexer.addDocument("", "empty name must be ignored");
        indexer.addDocument("   ", "whitespace-only name must be ignored");
        indexer.addDocument("EmptyContent", null);      // registers, matches nothing
        indexer.addDocument("SpecialOnly", "!!! ---   ;;;"); // tokenizes to nothing

        System.out.println("java python (k=2): " + indexer.search("java python", 2));
        // expect JavaGuru first (score 3: 2*java + 1*python), then PySnake (score 1: python)

        System.out.println("engineer (k=5): " + indexer.search("ENGINEER", 5));
        // expect [JavaGuru, PySnake, CaseTest] in some score order, case-insensitive match

        System.out.println("c++ (k=5): " + indexer.search("C++", 5));
        // expect [CppWizard] via "c" token match

        System.out.println("ruby tie-break (k=3): " + indexer.search("ruby", 3));
        // expect [Alpha, Mango, Zeta] - equal scores, alphabetical order

        System.out.println("ruby k=1: " + indexer.search("ruby", 1));
        // expect [Alpha] only - top 1 of the tie

        System.out.println("k=0: " + indexer.search("java", 0));
        // expect []

        System.out.println("negative k: " + indexer.search("java", -5));
        // expect []

        System.out.println("k bigger than matches: " + indexer.search("python", 100));
        // expect [JavaGuru, PySnake] - not padded to 100

        System.out.println("null query: " + indexer.search(null, 5));
        // expect []

        System.out.println("empty query: " + indexer.search("", 5));
        // expect []

        System.out.println("special-chars-only query: " + indexer.search("!!! ---", 5));
        // expect [] - tokenizes to nothing

        System.out.println("no matching token: " + indexer.search("zzzznotindexed", 5));
        // expect []

        System.out.println("empty-content doc never matches: " + indexer.search("emptycontent", 5));
        // expect [] - "EmptyContent" doc has no tokens, and query itself matches nothing indexed
    }
}
