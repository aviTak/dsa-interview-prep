import java.util.*;

class Solution {
    public static void main(String args[]) {
        int courses = 4, pre[][] = {{1,0},{2,1},{3,2},{1,3}};

        System.out.println(isPossible(courses, pre));
    }

    public static boolean isPossible(int courses, int[][] pre) {
        // Create adj list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < courses; i++) {
            adj.add(new ArrayList<Integer>());
        }

        int[] indegree = new int[courses];

        for (int[] p : pre) {
            adj.get(p[1]).add(p[0]);
            indegree[p[0]]++;
        }

        // Add all 0 indegrees into the queue
        Queue<Integer> que = new LinkedList<>();

        for (int i = 0; i < courses; i++) {
            if (indegree[i] == 0) {
                que.add(i);
            }
        }

        // Until queue is over
        int count = 0;

        while (!que.isEmpty()) {
            int node = que.poll();
            count++;

            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;

                if (indegree[neighbor] == 0) {
                    que.add(neighbor);
                }
            }
        }

        if (count == courses) {
            return true;
        }

        return false;
    }
}