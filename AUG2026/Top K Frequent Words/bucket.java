import java.util.*;

class Solution {
    public static void main(String args[]) {
        String words[] = {"i","love","leetcode","i","love","coding"};
        int k = 2;

        System.out.println(topK(words, k));
    }

    public static ArrayList<String> topK(String[] words, int k) {
        // 1. Find freq of each word

        HashMap<String, Integer> map = new HashMap<>();
        int N = words.length;

        for (int i = 0; i < N; i++) {
            map.put(words[i], map.getOrDefault(words[i], 0) + 1);
        }

        // 2. Create buckets of each frequency

        ArrayList<ArrayList<String>> bucket = new ArrayList<>(N + 1);

        for (int i = 0; i <= N; i++) {
            bucket.add(new ArrayList<>());
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();

            bucket.get(freq).add(word);
        }

        ArrayList<String> res = new ArrayList<>();

        for (int i = N; i >= 0 && k > 0; i--) {
            Collections.sort(bucket.get(i));

            for (String word : bucket.get(i)) {
                if (k > 0) {
                    res.add(word);
                    k--;
                }
            }
        }

        return res;
    }
}
