import java.util.*;

class Solution {
    public static void main(String args[]) {
        String words[] = {"b", "b", "a", "a", "a", "b", "c", "c", "c", "c", "d"};
        int k = 2;

        System.out.println(mostCommon(words, k));
    }

    
    public static List<String> mostCommon(String[] words, int k) {
        if (words == null || words.length == 0 || k <=0) {
            return new ArrayList<String>();
        }

        // Create a Frequency Map
        Map<String, Integer> freqMap = new HashMap<>();

        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        

        // Create a min heap
        PriorityQueue<String> pq = new PriorityQueue<>((w1, w2) -> {
            int f1 = freqMap.get(w1),
                f2 = freqMap.get(w2);

            if (f1 != f2) {
                return Integer.compare(f1, f2);
            }

            return w2.compareTo(w1);
        });

        // Add to min heap (only K elements)
        for (String word : freqMap.keySet()) {
            pq.offer(word);

            if (pq.size() > k) {
                pq.poll();
            }
        }

        List<String> res = new ArrayList<>();

        // Add the elements to the list
        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }

        Collections.reverse(res);
        return res;
    }
}
