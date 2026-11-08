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

        TreeNode rootb = new TreeNode(10);

        TreeNode n5b = new TreeNode(5),
            n15b = new TreeNode(15),
            n1b = new TreeNode(1),
            n8b = new TreeNode(8),
            n12b = new TreeNode(12),
            n20b = new TreeNode(20);

        rootb.left = n5b;
        rootb.right = n15b;

        n5b.left = n1b;
        n5b.right = n8b;

        n15b.left = n12b;
        n15b.right = n20b;

        System.out.println(isTreeEqual(root, rootb));
    }

    public static boolean isTreeEqual(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);

        while (!queue.isEmpty()) {
            TreeNode n1 = queue.poll(),
                n2 = queue.poll();

            if (n1 == null && n2 == null) {
                continue;
            }

            if (n1 == null || n2 == null || n1.val != n2.val) {
                return false;
            }

            queue.offer(n1.left);
            queue.offer(n2.left);
            queue.offer(n1.right);
            queue.offer(n2.right);
        }

        return true;
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

