import java.util.*;

class Solution {
    public static void main(String args[]) {
        BST tree = new BST();

        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(2);
        tree.add(4);

        System.out.println(tree.inorder());
        System.out.println(tree.kthLargest(2));
    }
}

class BST {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
            left = null;
            right = null;
        }
    }

    private TreeNode root;

    public BST() {
        root = null;
    }

    public void add(int val) {
        root = add(root, val);
    }

    public TreeNode add(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }

        if (val < node.val) {
            // Go left
            node.left = add(node.left, val);
        } else {
            // Go right
            node.right = add(node.right, val);
        }

        return node;
    }

    public ArrayList<Integer> inorder() {
        ArrayList<Integer> res = new ArrayList<>();

        inorder(root, res);
        return res;
    }

    public void inorder(TreeNode node, ArrayList<Integer> res) {
        if (node == null) {
            return;
        }

        inorder(node.left, res);

        res.add(node.val);

        inorder(node.right, res);
    }

    public int kthLargest(int k) {
        if (k == 0) {
            return -1;
        }

        Stack<TreeNode> stack = new Stack<>();

        TreeNode ptr = root;

        while (true) {
            while (ptr != null) {
                stack.push(ptr);
                ptr = ptr.right;
            }

            ptr = stack.pop();

            if (--k == 0) {
                return ptr.val;
            }

            ptr = ptr.left;
        }
    }
}
