import java.util.*;

public class GitCommitDAG {

    // ---- Step 0: class setup ----

    static class Commit {
        String hash;
        List<String> parents;
        long timestamp;
        String author;

        Commit(String hash, List<String> parents, long timestamp, String author) {
            this.hash = hash;
            this.parents = parents;
            this.timestamp = timestamp;
            this.author = author;
        }
    }

    private final Map<String, Commit> commitsByHash = new HashMap<>();
    private final Map<String, Integer> commitCountByAuthor = new HashMap<>();

    // ---- Step 1: addCommit ----
    public void addCommit(String hash, List<String> parents, long timestamp, String author) {
        if (hash == null) throw new IllegalArgumentException("hash cannot be null");
        if (author == null) throw new IllegalArgumentException("author cannot be null");

        List<String> safeParents = parents == null ? new ArrayList<>() : new ArrayList<>(parents);

        // re-adding an existing hash (e.g. an update): undo its old author-count contribution first
        Commit existing = commitsByHash.get(hash);
        if (existing != null) {
            commitCountByAuthor.computeIfPresent(existing.author, (a, count) -> count == 1 ? null : count - 1);
        }

        commitsByHash.put(hash, new Commit(hash, safeParents, timestamp, author));
        commitCountByAuthor.merge(author, 1, Integer::sum);
    }

    // ---- Step 2: getHistory ----
    public List<String> getHistory(String hash) {
        if (hash == null || !commitsByHash.containsKey(hash)) return new ArrayList<>();

        Set<String> visited = getAncestors(hash);
        visited.retainAll(commitsByHash.keySet()); // drop any dangling parent refs we have no data for

        List<String> history = new ArrayList<>(visited);
        history.sort((h1, h2) -> Long.compare(commitsByHash.get(h2).timestamp, commitsByHash.get(h1).timestamp));
        return history;
    }

    // helper: BFS up the parent edges, collecting every reachable commit (including the start hash)
    private Set<String> getAncestors(String hash) {
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();
        visited.add(hash);
        queue.add(hash);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Commit commit = commitsByHash.get(current);
            if (commit == null) continue; // dangling/unknown hash, skip

            for (String parent : commit.parents) {
                if (visited.add(parent)) {
                    queue.add(parent);
                }
            }
        }
        return visited;
    }

    // ---- Step 3: findLCA ----
    public String findLCA(String hashA, String hashB) {
        if (hashA == null || hashB == null
                || !commitsByHash.containsKey(hashA) || !commitsByHash.containsKey(hashB)) {
            return null;
        }

        Set<String> ancestorsA = getAncestors(hashA);
        Set<String> ancestorsB = getAncestors(hashB);

        Set<String> common = new HashSet<>(ancestorsA);
        common.retainAll(ancestorsB);
        common.retainAll(commitsByHash.keySet()); // ignore dangling parent refs we have no data for

        if (common.isEmpty()) return null; // no common ancestor at all

        // remove anyone whose parent is also in `common` — they're a further-back,
        // redundant ancestor since a closer common ancestor already covers them
        Set<String> dominated = new HashSet<>();
        for (String c : common) {
            Commit commit = commitsByHash.get(c);
            if (commit == null) continue;
            for (String parent : commit.parents) {
                if (common.contains(parent)) {
                    dominated.add(parent);
                }
            }
        }
        common.removeAll(dominated);

        // in the rare case of multiple, mutually-unrelated LCAs, break the tie deterministically
        return Collections.min(common);
    }

    // ---- Step 4: getTopContributors ----
    public List<String> getTopContributors(int n) {
        if (n <= 0) return new ArrayList<>();

        // min-heap of size n: worst-of-the-top-n sits at the head so it's cheap to evict
        PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>(
                (a, b) -> {
                    int byCount = Integer.compare(a.getValue(), b.getValue());
                    if (byCount != 0) return byCount;
                    return b.getKey().compareTo(a.getKey()); // alphabetically-last is "worse", evict first
                }
        );

        for (Map.Entry<String, Integer> entry : commitCountByAuthor.entrySet()) {
            heap.offer(entry);
            if (heap.size() > n) {
                heap.poll();
            }
        }

        List<String> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll().getKey());
        }

        // heap drains smallest-first; we want highest count / alphabetical first
        result.sort((a1, a2) -> {
            int byCount = Integer.compare(commitCountByAuthor.get(a2), commitCountByAuthor.get(a1));
            if (byCount != 0) return byCount;
            return a1.compareTo(a2);
        });
        return result;
    }

    // ---- dummy data / manual test ----
    public static void main(String[] args) {
        GitCommitDAG dag = new GitCommitDAG();

        // Graph shape (matches the earlier whiteboard example):
        //
        //   B ──────┐
        //   |        \
        //   C ────────┤
        //   |         |
        //   D         |
        //   |         |
        //   E         |
        //   |         |
        //   X         Y
        //             |
        //             Z
        //
        // B is root. Y is a merge commit with parents [B, C] (skips D, E).
        dag.addCommit("B", List.of(), 100, "Alice");
        dag.addCommit("C", List.of("B"), 200, "Bob");
        dag.addCommit("D", List.of("C"), 300, "Alice");
        dag.addCommit("E", List.of("D"), 400, "Charlie");
        dag.addCommit("X", List.of("E"), 500, "Alice");
        dag.addCommit("Y", List.of("B", "C"), 250, "Bob");
        dag.addCommit("Z", List.of("Y"), 600, "Dave");

        System.out.println("getHistory(X): " + dag.getHistory("X"));
        // expect [X, E, D, C, B] (timestamp descending)

        System.out.println("getHistory(Z): " + dag.getHistory("Z"));
        // expect [Z, Y, C, B] (timestamp descending: 600, 250, 200, 100)

        System.out.println("findLCA(X, Y): " + dag.findLCA("X", "Y"));
        // expect C (B is dominated, since C is a closer common ancestor)

        System.out.println("findLCA(X, Z): " + dag.findLCA("X", "Z"));
        // expect C, same reasoning through Z -> Y -> C

        System.out.println("findLCA(B, C): " + dag.findLCA("B", "C"));
        // expect B, since B is a direct ancestor of C

        System.out.println("getTopContributors(2): " + dag.getTopContributors(2));
        // counts: Alice=3 (B,D,X), Bob=2 (C,Y), Charlie=1 (E), Dave=1 (Z)
        // expect [Alice, Bob]

        System.out.println("getTopContributors(4): " + dag.getTopContributors(4));
        // expect [Alice, Bob, Charlie, Dave] — Charlie/Dave tie at 1, alphabetical breaks it

        // ---- edge cases ----

        System.out.println("getHistory(unknown): " + dag.getHistory("nope"));
        // expect [] (never added, no NPE)

        System.out.println("getHistory(null): " + dag.getHistory(null));
        // expect [] (no NPE from ArrayDeque)

        System.out.println("findLCA(X, unknown): " + dag.findLCA("X", "nope"));
        // expect null

        System.out.println("findLCA(null, Y): " + dag.findLCA(null, "Y"));
        // expect null

        System.out.println("findLCA(X, X): " + dag.findLCA("X", "X"));
        // expect X (self-LCA)

        // two disconnected roots -> no common ancestor at all
        dag.addCommit("W", List.of(), 50, "Eve");
        System.out.println("findLCA(B, W): " + dag.findLCA("B", "W"));
        // expect null

        // commit referencing a parent hash that was never added (dangling ref)
        dag.addCommit("V", List.of("ghost"), 700, "Eve");
        System.out.println("getHistory(V): " + dag.getHistory("V"));
        // expect [V] — "ghost" is dropped since we have no data for it, no NPE

        // null parents list should be treated as no parents, not crash
        dag.addCommit("U", null, 800, "Eve");
        System.out.println("getHistory(U): " + dag.getHistory("U"));
        // expect [U]

        // re-adding an existing hash under a different author shouldn't double-count
        dag.addCommit("W", List.of(), 50, "Frank"); // W was Eve's, now Frank's
        System.out.println("getTopContributors(10) after re-add: " + dag.getTopContributors(10));
        // Eve should now have 2 (V, U), not 3; Frank should have 1 (W)

        System.out.println("getTopContributors(0): " + dag.getTopContributors(0));
        // expect []

        System.out.println("getTopContributors(-5): " + dag.getTopContributors(-5));
        // expect []
    }
}
