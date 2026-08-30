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
        Commit commit = new Commit(hash, parents, timestamp, author);
        commitsByHash.put(hash, commit);
        commitCountByAuthor.merge(author, 1, Integer::sum);
    }

    // ---- Step 2: getHistory ----
    public List<String> getHistory(String hash) {
        Set<String> visited = getAncestors(hash);

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
        Set<String> ancestorsA = getAncestors(hashA);
        Set<String> ancestorsB = getAncestors(hashB);

        Set<String> common = new HashSet<>(ancestorsA);
        common.retainAll(ancestorsB);

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
}
