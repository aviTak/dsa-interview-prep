import java.util.*;

class Solution {
    public static void main(String args[]) {
        String words[] = {"b", "b", "a", "a", "a", "b", "c", "c", "c", "c", "d"};
        int k = 2;

        System.out.println(mostCommon(words, k));
    }

    public static List<String> mostCommon(String[] words, int k) {
        if (words == null || words.length == 0 || k <= 0) {
            return new ArrayList<>();
        }

        // Create a frequency map
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        // Create buckets indexed by frequency
        List<List<String>> buckets = new ArrayList<>(Collections.nCopies(words.length + 1, null));
        
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            int frequency = entry.getValue();

            if (buckets.get(frequency) == null) {
                buckets.set(frequency, new ArrayList<>());
            }
            buckets.get(frequency).add(entry.getKey());
        }

        // Sort each bucket alphabetically and collect top k by decreasing frequency
        List<String> result = new ArrayList<>();
        for (int i = words.length; i >= 1 && result.size() < k; i--) {
            if (buckets.get(i) != null) {
                Collections.sort(buckets.get(i));
                for (String word : buckets.get(i)) {
                    result.add(word);
                    if (result.size() == k) {
                        break;
                    }
                }
            }
        }

        return result;
    }
}