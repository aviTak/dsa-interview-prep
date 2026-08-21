import java.util.*;

class Solution {
    public static void main(String args[]) {
        int points[][] = {{1, 3}, {-2, 2}, {5, 8}, {0, 1}},
            k = 2;

        System.out.println(kthClosest(points, k));
    }

    public static ArrayList<ArrayList<Integer>> kthClosest(int[][] points, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int N = points.length;

        PriorityQueue<ArrayList<Integer>> pq = new PriorityQueue<>((a, b) -> {
            int distA = a.get(0) * a.get(0) + a.get(1) * a.get(1);
            int distB = b.get(0) * b.get(0) + b.get(1) * b.get(1);

            return distB - distA; 
        }); // Max Heap

        for (int i = 0; i < N; i++) {
            pq.add(new ArrayList<>(Arrays.asList(
                points[i][0],
                points[i][1]
            )));

            if (pq.size() > k) {
                pq.poll();
            }
        }

        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }

        return res;
    }
}
