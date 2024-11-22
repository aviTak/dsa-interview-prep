import java.util.*;

class KthSmallest {
    public static void main(String args[]) {
        int arr[] = { 10, 5, 4, 3, 48, 6, 2, 33, 53, 10 }, N = arr.length, K = 4;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int i = 0; i < N; i++) {
            pq.add(arr[i]);
            
            if (pq.size() > K) {
                pq.remove();
            }
        }
        
        System.out.println(pq.peek());
    }
}
