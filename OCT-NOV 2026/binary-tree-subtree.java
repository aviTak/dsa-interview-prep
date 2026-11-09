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

        TreeNode rootb = new TreeNode(15);

        TreeNode n12b = new TreeNode(12),
            n20b = new TreeNode(22);

        rootb.left = n12b;
        rootb.right = n20b;

        System.out.println(isSubTree(root, rootb));
    }

    public static boolean isSubTree(TreeNode node1, TreeNode node2) {
        if (node1 == null) {
            return false;
        }

        if (isSame(node1, node2)) {
            return true;
        }

        return isSubTree(node1.left, node2) ||
            isSubTree(node1.right, node2);
    }

    private static boolean isSame(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 == null || node2 == null) {
            return false;
        }

        if (node1.val != node2.val) {
            return false;
        }

        return isSame(node1.left, node2.left) &&
            isSame(node1.right, node2.right);
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

