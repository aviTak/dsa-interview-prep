import java.util.*;

class Solution {
    public static void main(String args[]) {
        int  num = 5, edges[][] = {{3,4},{0,3},{1,2},{0,1}};

        System.out.println(isTree(num, edges));
    }

    public static boolean isTree(int num, int[][] edges) {
        if (num <= 0) {
            return false;
        }

        int N = edges.length;

        if (num == 1 && N == 0) {
            return true;
        }

        if (num - 1 != N) {
            return false;
        }

        // Create an adjancey list
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            adj.add(new ArrayList<Integer>());
        }

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        boolean visited[] = new boolean[num];

        if (!dfs(0, -1, adj, visited)) {
            return false;
        }

        // Check if all nodes are visited or not
        for (int i = 0; i < num; i++) {
            if (!visited[i]) {
                return false;
            }
        }

        return true;
    }

    private static boolean dfs(int node, int parent, List<List<Integer>> adj, boolean[] visited) {
        visited[node] = true;

        // Check all neighbors
        for (int neighbor : adj.get(node)) {
            if (neighbor == parent) {
                continue;
            }

            if (visited[neighbor]) {
                return false;
            }

            if (!dfs(neighbor, node, adj, visited)) {
                return false;
            }
        }

        return true;
    }
}
