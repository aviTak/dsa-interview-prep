import java.util.*;

class Solution {
    public static void main(String args[]) {
        int num = 6, edges[][] = {{0,1},{4,5},{2,4},{1,3},{0,2}};

        System.out.println(countConnections(num, edges));
    }

    public static int countConnections(int num, int[][] edges) {
        int count = num;
        int[] parent = new int[num],
            rank = new int[num];

        for (int i = 0; i < num; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            if (union(edge[0], edge[1], parent, rank)) {
                count--;
            }
        }

        return count;
    }

    private static boolean union(int u, int v, int[] parent, int[] rank) {
        int rootU = find(u, parent),
            rootV = find(v, parent);

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

        return true;
    }

    private static int find(int node, int[] parent) {
        if (parent[node] == node) {
            return node;
        }

        return parent[node] = find(parent[node], parent);
    }
}