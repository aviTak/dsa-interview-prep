import java.util.*;

class Solution {
    public static void main(String args[]) {
        int courses = 4, pre[][] = {{1,0},{2,1},{3,2},{1,3}};

        System.out.println(isPossible(courses, pre));
    }

    public static boolean isPossible(int courses, int[][] pre) {
        // Create an adj list
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < courses; i++) {
            adj.add(new ArrayList<Integer>());
        }

        for (int p[] : pre) {
            adj.get(p[1]).add(p[0]);
        }

        // Have a visited and recStack for nodes
        boolean[] visited = new boolean[courses],
            recStack = new boolean[courses];

        // Visit all  nodes that are unvisited
        for (int i = 0; i < courses; i++) {
            if (!visited[i] && isLoop(adj, i, visited, recStack)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isLoop(List<List<Integer>> adj, int node, boolean[] visited, boolean[] recStack) {
        if (recStack[node]) {
            return true;
        }

        if (visited[node]) {
            return false;
        }

        visited[node] = true;
        recStack[node] = true;

        // Visit all its neighbors
        for (int neighbor : adj.get(node)) {
            if (isLoop(adj, neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack[node] = false;
        return false;
    }
}