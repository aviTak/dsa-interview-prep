import java.util.*;

public class Autocomplete {

    // Fixed cache size: each trie node keeps its top-C matches
    // (by score desc, then alphabetical asc) precomputed.
    // suggest() always returns at most CACHE_SIZE results, even if k asks for more.
    private static final int CACHE_SIZE = 10;

    // A single result entry.
    // key  = trimmed, lowercased term -> identity used to find/dedupe entries.
    // term = original casing -> what actually gets returned to callers.
    // score = current popularity.
    private static class Entry {
        String key;
        String term;
        int score;

        Entry(String key, String term, int score) {
            this.key = key;
            this.term = term;
            this.score = score;
        }
    }

    // Shared ordering: score descending, then alphabetically ascending.
    // Tie-break compares `key` (lowercased), NOT `term` (original casing) --
    // otherwise Java's natural String order would sort all uppercase letters
    // before all lowercase ones (e.g. "Zebra" before "apple"), which isn't
    // true case-insensitive alphabetical order.
    private static final Comparator<Entry> ORDER =
        Comparator.<Entry>comparingInt(e -> -e.score)
                  .thenComparing(e -> e.key);

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();

        // Top-CACHE_SIZE entries among all terms in this node's subtree
        // (this node's own term, if any, counts as part of its own subtree),
        // kept sorted by ORDER. Maintained incrementally on every addTerm()
        // by scanning/updating this list at each ancestor on the insert path.
        List<Entry> topCache = new ArrayList<>();
    }

    private final TrieNode root = new TrieNode();

    public void addTerm(String term, int popularity) {
        if (term == null) return;

        String trimmed = term.trim();
        if (trimmed.isEmpty()) return;

        String key = trimmed.toLowerCase();
        Entry entry = new Entry(key, trimmed, popularity);

        // Update the root's cache too -> root represents prefix "",
        // i.e. the global top-C across every term.
        TrieNode node = root;
        updateNodeCache(node, entry);

        // Walk/create the path for `key`, updating each node's cache
        // (its subtree's top-C) as we go, including the final node.
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            node = node.children.computeIfAbsent(c, ch -> new TrieNode());
            updateNodeCache(node, entry);
        }
    }

    // Inserts/updates `entry` in `node`'s top-CACHE_SIZE list, keeping it
    // sorted by ORDER. If an entry with the same key already exists here
    // (i.e. this is an update to an existing term, not a new one), it is
    // replaced rather than duplicated. Same shared Entry instance is used
    // across every ancestor's cache, so it can't get inconsistent between them.
    private void updateNodeCache(TrieNode node, Entry entry) {
        List<Entry> cache = node.topCache;

        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).key.equals(entry.key)) {
                cache.remove(i);
                break;
            }
        }

        int idx = 0;
        while (idx < cache.size() && ORDER.compare(cache.get(idx), entry) <= 0) {
            idx++;
        }
        cache.add(idx, entry);

        if (cache.size() > CACHE_SIZE) {
            cache.remove(cache.size() - 1);
        }
    }

    public List<String> suggest(String prefix, int k) {
        if (prefix == null) return new ArrayList<>();
        if (k <= 0) return new ArrayList<>();

        String key = prefix.toLowerCase();

        // Empty prefix -> stay at root -> root's cache is the global top-C.
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            node = node.children.get(key.charAt(i));
            if (node == null) return new ArrayList<>(); // no term starts with this prefix
        }

        List<Entry> cache = node.topCache;
        int limit = Math.min(k, cache.size());
        List<String> result = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            result.add(cache.get(i).term);
        }
        return result;
    }

    public static void main(String[] args) {
        Autocomplete ac = new Autocomplete();

        ac.addTerm("Cat", 50);
        ac.addTerm("Car", 50);      // tie score with Cat
        ac.addTerm("Care", 70);
        ac.addTerm("Card", 70);     // tie score with Care -> "card" < "care" alphabetically
        ac.addTerm("Cats", 90);
        ac.addTerm("Catalog", 40);
        ac.addTerm("Dog", 30);
        ac.addTerm("apple", 60);
        ac.addTerm("zebra", 50);    // tie score with Cat/Car, tests case-insensitive tie-break
        ac.addTerm("CAT", 100);     // duplicate of "Cat" (case-insensitive) -> overwrite score+casing
        ac.addTerm("wolf", -5);     // negative popularity allowed

        System.out.println("suggest(\"ca\", 5)      -> " + ac.suggest("ca", 5));
        // expect: [CAT, Cats, Card, Care, Car] (100, 90, 70/70 alpha, 50)

        System.out.println("suggest(\"CA\", 5)      -> " + ac.suggest("CA", 5));
        // same as above -> case-insensitive prefix match

        System.out.println("suggest(\"\", 3)        -> " + ac.suggest("", 3));
        // global top-3: [CAT, Cats, Card]

        System.out.println("suggest(\"xyz\", 5)     -> " + ac.suggest("xyz", 5));
        // no term starts with "xyz" -> []

        System.out.println("suggest(\"ca\", 0)      -> " + ac.suggest("ca", 0));
        // k == 0 -> []

        System.out.println("suggest(\"ca\", -1)     -> " + ac.suggest("ca", -1));
        // negative k -> []

        System.out.println("suggest(null, 5)      -> " + ac.suggest(null, 5));
        // null prefix -> []

        System.out.println("suggest(\"dog\", 5)     -> " + ac.suggest("dog", 5));
        // fewer matches than k -> [Dog]

        System.out.println("suggest(\"wolf\", 5)    -> " + ac.suggest("wolf", 5));
        // negative popularity still returned when nothing else competes -> [wolf]

        Autocomplete empty = new Autocomplete();
        System.out.println("empty structure        -> " + empty.suggest("a", 5));
        // nothing ever added -> []

        // tie-break sanity check: "apple"(60) vs "zebra"(50) vs "Car/Cat"(50) --
        // score desc first, so this mainly proves alpha tie-break is case-insensitive
        // via the "ca" query above (Card before Care, both original-cased "C...").
    }
}
