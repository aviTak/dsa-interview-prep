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

        System.out.println(kthSmallest(root, 2));
    }

    public static int kthSmallest(TreeNode root, int k) {
        if (root == null || k <= 0) {
            return -1;
        }

        TreeNode curr = root;
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (curr != null || !stack.isEmpty()) { // Aisa bhi ho sakta ki right mein koi child hi na ho
            // Go all the way till left
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop(); // Left most
            k--;

            if (k == 0) {
                return curr.val;
            }

            // Else go all to its right, then in the next loop all the way to its left
            curr = curr.right;
        }

        return -1;
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
