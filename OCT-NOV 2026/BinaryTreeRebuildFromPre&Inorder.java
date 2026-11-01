import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] preorder = {3,1,2,6,5,9}, inorder = {1,2,3,5,6,9};

        BinaryTree bt = new BinaryTree();
        bt.rebuild(preorder, inorder);
        bt.postOrder();
    }
}

public class BinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private TreeNode root;
    private int preIndex;
    private Map<Integer, Integer> inorderMap;

    public BinaryTree() {
        preIndex = 0;
        inorderMap = new HashMap<Integer, Integer>();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void rebuild(int[] preorder, int[] inorder) {
        // Create a map of inorder
        int N = inorder.length;

        for (int i = 0; i < N; i++) {
            inorderMap.put(inorder[i], i);
        }

        preIndex = 0;
        this.root = rebuild(preorder, 0, N - 1);
    }

    private TreeNode rebuild(int[] preorder, int i, int j) {
        if (i > j) {
            return null;
        }

        int rootVal = preorder[preIndex++];

        TreeNode node = new TreeNode(rootVal);

        int mid = inorderMap.get(rootVal);

        node.left = rebuild(preorder, i, mid - 1);
        node.right = rebuild(preorder, mid + 1, j);

        return node;
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.val);
    }
}
