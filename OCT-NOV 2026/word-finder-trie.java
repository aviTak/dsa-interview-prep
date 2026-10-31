import java.util.*;

class Main {
    public static void main(String args[]) {
        WordFinder k = new WordFinder();
        k.addWord("car");
        k.addWord("cat");

        System.out.println(k.search("c.r"));

        k.addWord("dog");

        System.out.println(k.search("d.g"));

        System.out.println(k.search("cat"));
        System.out.println(k.search("c.."));
    }
}

class WordFinder {
    private static class TrieNode {
        TrieNode children[];
        boolean isWord;

        TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
    }

    private final TrieNode root;

    public WordFinder() {
        root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode node = root;
        int N = word.length();

        for (int i = 0; i < N; i++) {
            int id = word.charAt(i) - 'a';

            if (node.children[id] == null) {
                node.children[id] = new TrieNode();
            }

            node = node.children[id];
        }

        node.isWord = true;
    }

    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int index, TrieNode node) {
        if (word.length() == index) {
            return node.isWord;
        }

        char g = word.charAt(index);

        if (g == '.') {
            // Check all children
            for (TrieNode child : node.children) {
                if (child != null && dfs(word, index + 1, child)) {
                    return true;
                }
            }
            return false;
        } else {
            if (node.children[g - 'a'] == null) {
                return false;
            }
            return dfs(word, index + 1, node.children[g - 'a']);
        }
    }
}
