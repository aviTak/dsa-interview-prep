import java.util.*;

class Solution {
    public static void main(String args[]) {
        TreeNode root = new TreeNode(10);

        root.left = new TreeNode(5);
        root.right = new TreeNode(15);

        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(8);

        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(20);

        System.out.println(validate(root));
    }

    public static boolean validate(TreeNode root) {
        return validate(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, int min, int max) {
        if (node == null) {
            // Reached leaf node
            return true;
        }

        if (node.val >= max || node.val <= min) {
            return false;
        }

        return validate(node.left, min, node.val) &&
            validate(node.right, node.val, max);
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



