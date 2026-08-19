import java.util.*;

class MostCommon {
    public static void main(String args[]) {
        int numbers[] = {4,4,4,4,6,6,5,5,5}, K = 2;
        System.out.println(common(numbers, K));
    }

    public static ArrayList<Integer> common(int numbers[], int K) {
        HashMap<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b)); // Min Heap

        int N = numbers.length;

        for (int i = 0; i < N; i++) {
            map.put(numbers[i], map.getOrDefault(numbers[i], 0) + 1);
        }

        for (int key : map.keySet()) {
            pq.add(key);

            if (pq.size() > K) {
                pq.poll();
            }
        }

        ArrayList<Integer> res = new ArrayList<>();

        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }

        Collections.reverse(res);
        return res;
    }
}
