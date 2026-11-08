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

        System.out.println(levelOrderTraversal(root));
    }

    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int N = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                TreeNode t = queue.poll();
                level.add(t.val);
                
                if (t.left != null) {
                    queue.offer(t.left);
                }

                if (t.right != null) {
                    queue.offer(t.right);
                }
            }

            res.add(level);
        }

        return res;
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

