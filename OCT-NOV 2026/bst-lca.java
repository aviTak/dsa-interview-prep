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

        TreeNode ancestor = lca(root, n12, n20);
        System.out.println(ancestor != null ? ancestor.val : null);
    }

    public static TreeNode lca(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || node1 == null || node2 == null) {
            return null;
        }

        TreeNode node = root;

        while (node != null) {
            if (node1.val > node.val && node2.val > node.val) {
                node = node.right;
            } else if (node1.val < node.val && node2.val < node.val) {
                node = node.left;
            } else {
                return node;
            }
        }

        return node;
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

