import java.util.*;

class Trie {
    class TrieNode {
        TrieNode[] children;
        boolean isLeaf;

        TrieNode() {
            this.isLeaf = false;
            this.children = new TrieNode[26];
        }
    }

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String key) {
        TrieNode curr = root;

        char[] letters = key.toCharArray();
        int N = letters.length;

        for (int i = 0; i < N; i++) {
            char g = letters[i];

            if (curr.children[g - 97] == null) {
                curr.children[g - 97] = new TrieNode();
            }

            curr = curr.children[g - 97];
        }

        curr.isLeaf = true;
    }

    public boolean search(String key) {
        TrieNode curr = root;

        char[] letters = key.toCharArray();
        int N = letters.length;

        for (int i = 0; i < N; i++) {
            char g = letters[i];

            if (curr.children[g - 97] == null) {
                return false;
            }

            curr = curr.children[g - 97];
        }

        if (curr.isLeaf) {
            return true;
        }

        return false;
    }

    public boolean isPrefix(String key) {
        TrieNode curr = root;

        char[] letters = key.toCharArray();
        int N = letters.length;

        for (int i = 0; i < N; i++) {
            char g = letters[i];

            if (curr.children[g - 97] == null) {
                return false;
            }

            curr = curr.children[g - 97];
        }

        return true;
    }

    public static void main(String args[]) {
        Trie trie = new Trie();
        String[] arr
            = {"and", "ant", "do", "dad"};
        for (String s : arr) {
            trie.insert(s);
        }
        String[] searchKeys = { "do", "gee", "bat" };
        for (String s : searchKeys) {
            if (trie.search(s))
                System.out.print("true ");
            else
                System.out.print("false ");
        }
        System.out.println();
        String[] prefixKeys = { "ge", "ba", "do", "de" };
        for (String s : prefixKeys) {
            if (trie.isPrefix(s))
                System.out.print("true ");
            else
                System.out.print("false ");
        }
    }
}