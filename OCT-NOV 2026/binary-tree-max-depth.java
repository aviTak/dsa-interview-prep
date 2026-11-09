import java.util.*;

class Solution {
    public static void main(String args[]) {
        TreeNode root = new TreeNode(10);

        TreeNode n5 = new TreeNode(5),
            n15 = new TreeNode(15),
            n1 = new TreeNode(1),
            n8 = new TreeNode(8),
            n12 = new TreeNode(12),
            n20 = new TreeNode(20);

        root.left = n5;
        root.right = n15;

        n5.left = n1;
        n5.right = n8;

        n15.left = n12;
        n15.right = n20;

        display(root);
    }

    public static void display(TreeNode node) {
        if (node == null) {
            return;
        }

        display(node.left);
        System.out.print(node.val + " ");
        display(node.right);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode() {
        val = 0;
    }

    public TreeNode(int val) {
        this.val = val;
    }
}
