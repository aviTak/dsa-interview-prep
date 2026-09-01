import java.util.*;

public class autocomplete {

    // Helper class to store a term and its properties
    static class TermEntry {
        String canonicalKey; // Lowercase version for case-insensitive matching & deduplication
        String originalTerm; // Original display version with actual casing
        int popularity;      // Popularity score (can be positive, 0, or negative)

        TermEntry(String canonicalKey, String originalTerm, int popularity) {
            this.canonicalKey = canonicalKey;
            this.originalTerm = originalTerm;
            this.popularity = popularity;
        }
    }

    // Memory-optimized Trie Node
    static class TrieNode {
        Map<Character, TrieNode> children;         // Dynamically allocated children map
        TermEntry termEntry;                       // Non-null if a full term ends at this node
        int maxSubtreePopularity;                  // Maximum popularity score in this entire subtree

        TrieNode() {
            this.children = new HashMap<>();
            this.termEntry = null;
            this.maxSubtreePopularity = Integer.MIN_VALUE;
        }
    }

    private final TrieNode root;

    public autocomplete() {
        this.root = new TrieNode();
    }

    /**
     * Inserts or updates a term with its popularity.
     * Overwrites popularity and display string if canonical term already exists.
     * Updates maxSubtreePopularity on the path bottom-up with early-break optimization.
     */
    public void addTerm(String term, int popularity) {
        if (term == null || term.isEmpty()) {
            return; // Ignore empty or null terms
        }

        String canonicalKey = term.toLowerCase();
        TrieNode curr = root;
        List<TrieNode> path = new ArrayList<>();
        path.add(curr);

        for (int i = 0; i < canonicalKey.length(); i++) {
            char c = canonicalKey.charAt(i);
            curr.children.putIfAbsent(c, new TrieNode());
            curr = curr.children.get(c);
            path.add(curr);
        }

        // Insert new or overwrite existing term entry
        if (curr.termEntry == null) {
            curr.termEntry = new TermEntry(canonicalKey, term, popularity);
        } else {
            curr.termEntry.originalTerm = term;
            curr.termEntry.popularity = popularity;
        }

        // Recalculate maxSubtreePopularity bottom-up along the path back to root
        for (int i = path.size() - 1; i >= 0; i--) {
            TrieNode node = path.get(i);
            int max = (node.termEntry != null) ? node.termEntry.popularity : Integer.MIN_VALUE;
            for (TrieNode child : node.children.values()) {
                max = Math.max(max, child.maxSubtreePopularity);
            }

            // Early-break optimization: if max score didn't change, ancestors won't either
            if (node.maxSubtreePopularity == max) {
                break;
            }
            node.maxSubtreePopularity = max;
        }
    }

    /**
     * Suggests top k matching terms for a given prefix.
     * Uses Best-First Search with Branch & Bound pruning across Trie nodes.
     */
    public List<String> suggest(String prefix, int k) {
        if (k <= 0) {
            return Collections.emptyList();
        }

        if (prefix == null) {
            prefix = "";
        }

        String canonicalPrefix = prefix.toLowerCase();
        TrieNode curr = root;

        // 1. Locate the prefix node
        for (int i = 0; i < canonicalPrefix.length(); i++) {
            char c = canonicalPrefix.charAt(i);
            curr = curr.children.get(c);
            if (curr == null) {
                return Collections.emptyList(); // Prefix not found
            }
        }

        // 2. Max-Heap to explore the most promising branches first
        PriorityQueue<TrieNode> explorationMaxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.maxSubtreePopularity, a.maxSubtreePopularity)
        );

        // 3. Min-Heap (Leaderboard) keeping top-k terms; evicts worst item when size > k
        PriorityQueue<TermEntry> topKCollector = new PriorityQueue<>((a, b) -> {
            if (a.popularity != b.popularity) {
                return Integer.compare(a.popularity, b.popularity); // lower score evicted first
            }
            int cmp = b.canonicalKey.compareTo(a.canonicalKey); // alphabetically larger evicted first
            if (cmp != 0) return cmp;
            return b.originalTerm.compareTo(a.originalTerm);
        });

        explorationMaxHeap.offer(curr);

        // 4. Best-First Search with Branch & Bound Pruning
        while (!explorationMaxHeap.isEmpty()) {
            TrieNode node = explorationMaxHeap.peek();

            // Pruning check: Stop if next best branch cannot beat our worst top-k candidate
            if (topKCollector.size() == k && 
                node.maxSubtreePopularity < topKCollector.peek().popularity) {
                break;
            }

            node = explorationMaxHeap.poll();

            // If this node is a complete word, add to candidate collector
            if (node.termEntry != null) {
                topKCollector.offer(node.termEntry);
                if (topKCollector.size() > k) {
                    topKCollector.poll(); // Discard the worst
                }
            }

            // Add children to exploration heap (skipping subtrees that cannot beat top-k)
            for (TrieNode child : node.children.values()) {
                if (topKCollector.size() == k && 
                    child.maxSubtreePopularity < topKCollector.peek().popularity) {
                    continue;
                }
                explorationMaxHeap.offer(child);
            }
        }

        // 5. Build final result list in descending order of rank
        LinkedList<String> result = new LinkedList<>();
        while (!topKCollector.isEmpty()) {
            result.addFirst(topKCollector.poll().originalTerm);
        }

        return result;
    }

    public static void main(String[] args) {
        autocomplete engine = new autocomplete();

        // 1. Basic insertion with mixed casing & special characters
        engine.addTerm("Java Developer", 100);
        engine.addTerm("Java Architect", 150);
        engine.addTerm("JavaScript Engineer", 120);
        engine.addTerm("Java", 80);
        engine.addTerm("C++ Developer", 200);
        engine.addTerm("Node.js Backend", 130);

        System.out.println("--- Test 1: Prefix 'jav' top 3 ---");
        System.out.println(engine.suggest("jav", 3));
        // Expected: [Java Architect, JavaScript Engineer, Java Developer]

        System.out.println("\n--- Test 2: Case-insensitive query 'JAVA' top 2 ---");
        System.out.println(engine.suggest("JAVA", 2));
        // Expected: [Java Architect, JavaScript Engineer]

        System.out.println("\n--- Test 3: Overwrite popularity of 'java developer' from 100 to 300 ---");
        engine.addTerm("java developer", 300);
        System.out.println(engine.suggest("jav", 3));
        // Expected: [java developer, Java Architect, JavaScript Engineer]

        System.out.println("\n--- Test 4: Tie-breaking alphabetical with same popularity ---");
        engine.addTerm("Data Analyst", 50);
        engine.addTerm("Data Scientist", 50);
        engine.addTerm("Database Admin", 50);
        System.out.println(engine.suggest("data", 3));
        // Expected: [Data Analyst, Data Scientist, Database Admin]

        System.out.println("\n--- Test 5: Negative popularity handling ---");
        engine.addTerm("Legacy Fortran Dev", -10);
        engine.addTerm("Legacy COBOL Dev", -2);
        engine.addTerm("Legacy Assembly Dev", -5);
        System.out.println(engine.suggest("legacy", 3));
        // Expected: [Legacy COBOL Dev, Legacy Assembly Dev, Legacy Fortran Dev] (-2 > -5 > -10)

        System.out.println("\n--- Test 6: Empty prefix '' (returns top 3 overall) ---");
        System.out.println(engine.suggest("", 3));
        // Expected: [java developer (300), C++ Developer (200), Java Architect (150)]

        System.out.println("\n--- Test 7: Edge cases (k=0, k > total matches) ---");
        System.out.println("k=0: " + engine.suggest("java", 0)); // []
        System.out.println("k=100 (more than total): " + engine.suggest("node", 100)); // [Node.js Backend]
    }
}
