import java.util.*;

public class git {

    // Step 0: Commit Data Model
    public record Commit(String hash, List<String> parents, long timestamp, String author) {}

    // Internal State
    private final Map<String, Commit> commits;
    private final Map<String, Integer> authorCounts;

    public git() {
        this.commits = new HashMap<>();
        this.authorCounts = new HashMap<>();
    }

    // Step 1: Add a commit to the DAG
    public void addCommit(String hash, List<String> parents, long timestamp, String author) {
        if (hash == null || hash.isEmpty() || author == null || author.isEmpty()) {
            return;
        }
        if (commits.containsKey(hash)) {
            return; // Ignore duplicate
        }

        List<String> validParents = (parents == null) ? List.of() : List.copyOf(parents);
        Commit commit = new Commit(hash, validParents, timestamp, author);

        commits.put(hash, commit);
        authorCounts.put(author, authorCounts.getOrDefault(author, 0) + 1);
    }

    // Step 2: Breadth-First Search History Traversal
    public List<String> getHistory(String hash) {
        if (hash == null || !commits.containsKey(hash)) {
            return Collections.emptyList();
        }

        List<String> history = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        queue.offer(hash);
        visited.add(hash);

        while (!queue.isEmpty()) {
            String currHash = queue.poll();
            history.add(currHash);

            Commit curr = commits.get(currHash);
            if (curr == null || curr.parents() == null) {
                continue;
            }

            // Sibling parents tie-break: timestamp desc, hash asc
            List<String> parents = new ArrayList<>(curr.parents());
            parents.sort((p1, p2) -> {
                Commit c1 = commits.get(p1);
                Commit c2 = commits.get(p2);
                if (c1 != null && c2 != null && c1.timestamp() != c2.timestamp()) {
                    return Long.compare(c2.timestamp(), c1.timestamp());
                }
                return p1.compareTo(p2);
            });

            for (String parentHash : parents) {
                if (commits.containsKey(parentHash) && visited.add(parentHash)) {
                    queue.offer(parentHash);
                }
            }
        }

        return history;
    }

    // Step 3: Lowest Common Ancestor in a DAG (Git Merge-Base)
    public String findLCA(String hashA, String hashB) {
        if (hashA == null || hashB == null) {
            return null;
        }
        if (!commits.containsKey(hashA) || !commits.containsKey(hashB)) {
            return null;
        }
        if (hashA.equals(hashB)) {
            return hashA;
        }

        // Reusing getHistory to collect all reachable ancestors
        Set<String> ancestorsA = new HashSet<>(getHistory(hashA));
        Set<String> ancestorsB = new HashSet<>(getHistory(hashB));

        // Find intersection (all common ancestors)
        Set<String> common = new HashSet<>(ancestorsA);
        common.retainAll(ancestorsB);

        if (common.isEmpty()) {
            return null;
        }

        // Prune non-lowest ancestors (ancestors of any other common ancestor)
        Set<String> redundant = new HashSet<>();
        for (String cHash : common) {
            Commit c = commits.get(cHash);
            if (c != null && c.parents() != null) {
                for (String parent : c.parents()) {
                    redundant.addAll(getHistory(parent));
                }
            }
        }
        common.removeAll(redundant);

        // Tie-breaker: Highest timestamp, then lexicographical hash order
        String bestLCA = null;
        Commit bestCommit = null;

        for (String candidateHash : common) {
            Commit candidate = commits.get(candidateHash);
            if (candidate == null) {
                continue;
            }

            if (bestCommit == null) {
                bestLCA = candidateHash;
                bestCommit = candidate;
            } else if (candidate.timestamp() > bestCommit.timestamp()) {
                bestLCA = candidateHash;
                bestCommit = candidate;
            } else if (candidate.timestamp() == bestCommit.timestamp() && candidateHash.compareTo(bestLCA) < 0) {
                bestLCA = candidateHash;
                bestCommit = candidate;
            }
        }

        return bestLCA;
    }

    // Step 4: Top N Contributors using Min-Heap (O(U log N))
    public List<String> getTopContributions(int n) {
        if (n <= 0 || authorCounts.isEmpty()) {
            return Collections.emptyList();
        }

        // Min-Heap comparator: orders from LEAST desirable to MOST desirable
        Comparator<Map.Entry<String, Integer>> minHeapComp = (e1, e2) -> {
            // 1. Lower commit count is less desirable (evicted first)
            int countCmp = Integer.compare(e1.getValue(), e2.getValue());
            if (countCmp != 0) {
                return countCmp;
            }

            // 2. Lexicographically larger name (case-insensitive) is less desirable
            int caseInsensitiveCmp = String.CASE_INSENSITIVE_ORDER.compare(e2.getKey(), e1.getKey());
            if (caseInsensitiveCmp != 0) {
                return caseInsensitiveCmp;
            }

            // 3. Lexicographically larger name (case-sensitive) is less desirable
            return e2.getKey().compareTo(e1.getKey());
        };

        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(minHeapComp);

        for (Map.Entry<String, Integer> entry : authorCounts.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > n) {
                minHeap.poll(); // Evict the least desirable
            }
        }

        // Extract in order from most desirable to least desirable
        LinkedList<String> result = new LinkedList<>();
        while (!minHeap.isEmpty()) {
            result.addFirst(minHeap.poll().getKey());
        }

        return result;
    }

    // ==========================================
    // Testing & Edge Cases
    // ==========================================
    public static void main(String[] args) {
        git repo = new git();

        System.out.println("=== 1. Testing addCommit & Null/Empty Edge Cases ===");
        repo.addCommit(null, null, 100, "Alice"); // Should ignore
        repo.addCommit("", null, 100, "Alice");   // Should ignore
        repo.addCommit("c0", null, 100, null);    // Should ignore

        // Valid Root Commit (parents = null)
        repo.addCommit("c0", null, 100, "Alice");
        repo.addCommit("c0", null, 100, "Alice"); // Duplicate, should ignore & not double count author

        // Linear Branching
        repo.addCommit("c1", List.of("c0"), 110, "Bob");
        repo.addCommit("c2", List.of("c0"), 120, "charlie");

        // Merge Commit (Diamond)
        repo.addCommit("c3", List.of("c1", "c2"), 130, "Alice");

        System.out.println("History of c3: " + repo.getHistory("c3"));
        // Expected: [c3, c2, c1, c0] (c2 has higher timestamp 120 than c1 110)

        System.out.println("\n=== 2. Testing getHistory Edge Cases ===");
        System.out.println("History of null: " + repo.getHistory(null)); // Expected: []
        System.out.println("History of non-existent: " + repo.getHistory("unknown")); // Expected: []
        System.out.println("History of root c0: " + repo.getHistory("c0")); // Expected: [c0]

        System.out.println("\n=== 3. Testing findLCA ===");
        // Simple Branch LCA
        System.out.println("LCA(c1, c2): " + repo.findLCA("c1", "c2")); // Expected: c0
        // Merge vs Branch LCA
        System.out.println("LCA(c3, c1): " + repo.findLCA("c3", "c1")); // Expected: c1 (c1 is ancestor of c3)
        // Same commit LCA
        System.out.println("LCA(c3, c3): " + repo.findLCA("c3", "c3")); // Expected: c3
        // Non-existent or null LCA
        System.out.println("LCA(null, c1): " + repo.findLCA(null, "c1")); // Expected: null
        System.out.println("LCA(unknown, c1): " + repo.findLCA("unknown", "c1")); // Expected: null

        System.out.println("\n=== 4. Testing Criss-Cross LCA with Timestamp Tie-breaker ===");
        // Disjoint Root Commit
        repo.addCommit("r1", null, 50, "Dave");
        System.out.println("LCA(c3, r1) [Disjoint branches]: " + repo.findLCA("c3", "r1")); // Expected: null

        // Criss-Cross graph:
        // x1 (time 200), x2 (time 250)
        // m1 parents: [x1, x2]
        // m2 parents: [x1, x2]
        repo.addCommit("x1", null, 200, "Eve");
        repo.addCommit("x2", null, 250, "Eve");
        repo.addCommit("m1", List.of("x1", "x2"), 300, "Eve");
        repo.addCommit("m2", List.of("x1", "x2"), 310, "Eve");

        // Both x1 and x2 are LCAs, but x2 has higher timestamp (250 > 200)
        System.out.println("LCA(m1, m2) [Tie-break on timestamp]: " + repo.findLCA("m1", "m2")); // Expected: x2

        // Equal timestamp tie-break on hash lexicographically:
        repo.addCommit("y_b", null, 400, "Frank");
        repo.addCommit("y_a", null, 400, "Frank");
        repo.addCommit("n1", List.of("y_a", "y_b"), 500, "Frank");
        repo.addCommit("n2", List.of("y_a", "y_b"), 510, "Frank");
        // Both y_a and y_b have same timestamp 400 -> "y_a" comes before "y_b" lexicographically
        System.out.println("LCA(n1, n2) [Tie-break on equal timestamp by hash]: " + repo.findLCA("n1", "n2")); // Expected: y_a

        System.out.println("\n=== 5. Testing getTopContributions Tie-breaking ===");
        git statsRepo = new git();
        // Setup authors with specific counts and case variations:
        statsRepo.addCommit("t1", null, 1, "alice"); // alice: 2 commits
        statsRepo.addCommit("t2", null, 2, "alice");

        statsRepo.addCommit("t3", null, 3, "Alice"); // Alice: 2 commits
        statsRepo.addCommit("t4", null, 4, "Alice");

        statsRepo.addCommit("t5", null, 5, "Bob");   // Bob: 2 commits
        statsRepo.addCommit("t6", null, 6, "Bob");

        statsRepo.addCommit("t7", null, 7, "Zack");  // Zack: 3 commits
        statsRepo.addCommit("t8", null, 8, "Zack");
        statsRepo.addCommit("t9", null, 9, "Zack");

        // Zack (3 commits) -> 1st
        // For Alice, alice, Bob (2 commits each):
        // Case-insensitive alphabetical: "Alice"/"alice" before "Bob"
        // Case-sensitive tie: "Alice" (ASCII 65) before "alice" (ASCII 97)
        // Expected Top 4: [Zack, Alice, alice, Bob]

        System.out.println("Top 4 contributors: " + statsRepo.getTopContributions(4));
        // Expected: [Zack, Alice, alice, Bob]

        System.out.println("Top 2 contributors: " + statsRepo.getTopContributions(2));
        // Expected: [Zack, Alice]

        System.out.println("Top 0 contributors: " + statsRepo.getTopContributions(0));
        // Expected: []

        System.out.println("Top 10 (n > total authors): " + statsRepo.getTopContributions(10));
        // Expected: [Zack, Alice, alice, Bob]
    }
}
