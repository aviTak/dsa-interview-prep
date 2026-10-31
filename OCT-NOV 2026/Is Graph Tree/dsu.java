import java.util.*;

class Solution {
    public static void main(String args[]) {
        int num = 5, edges[][] = {{3,4},{0,3},{1,2},{0,1}};

        System.out.println(isTree(num, edges));
    }

    public static boolean isTree(int num, int[][] edges) {
        if (num <= 0) {
            return false;
        }

        if (edges == null) {
            return false;
        }

        int N = edges.length;

        if (num - 1 != N) {
            return false;
        }

        // Make every node parent of itself
        int[] parent = new int[num],
            rank = new int[num];

        for (int i = 0; i < num; i++) {
            parent[i] = i;
        }

        // Process each edge
        for (int[] edge : edges) {
            int rootU = find(edge[0], parent),
                rootV = find(edge[1], parent);

            if (rootU == rootV) {
                return false;
            }

            // Union by rank
            if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else {
                parent[rootU] = rootV;
                rank[rootV]++;
            }
            
        }

        return true;
    }

    private static int find(int node, int[] parent) {
        if (parent[node] == node) {
            return node;
        }

        return parent[node] = find(parent[node], parent);
    }
}