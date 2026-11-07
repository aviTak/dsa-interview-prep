import java.util.*;

class Solution {
    public static void main(String args[]) {
        Trie t = new Trie();
        t.insert("car");
        t.insert("care");
        System.out.println(t.search("car"));
        System.out.println(t.startsWith("cap"));
        t.insert("card");
        System.out.println(t.search("cast"));
        System.out.println(t.startsWith("car"));
    }
}

class Trie {
    private static class Node {
        private final Node[] children;
        private boolean isEnd;

        Node() {
            children = new Node[26];
            isEnd = false;
        }
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        word = word.toLowerCase();
        int N = word.length();
        Node node = root;

        for (int i = 0; i < N; i++) {
            char g = word.charAt(i);

            if (node.children[g - 97] == null) {
                node.children[g - 97] = new Node();
            }

            node = node.children[g - 97];
        }

        node.isEnd = true;
    }

    public boolean search(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        word = word.toLowerCase();
        int N = word.length();
        Node node = root;

        for (int i = 0; i < N; i++) {
            char g = word.charAt(i);

            if (node.children[g - 97] == null) {
                return false;
            }

            node = node.children[g - 97];
        }

        return node.isEnd;
    }

    public boolean startsWith(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        word = word.toLowerCase();
        int N = word.length();
        Node node = root;

        for (int i = 0; i < N; i++) {
            char g = word.charAt(i);

            if (node.children[g - 97] == null) {
                return false;
            }

            node = node.children[g - 97];
        }

        return true;
    }    
}
