import java.util.*;

class Main {
    public static void main(String args[]) {
        String strs[] = {"abc","bca","cab","xyz","zyx"};

        System.out.println(anagramGroups(strs));
    }

    public static ArrayList<ArrayList<String>> anagramGroups(String[] strs) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();

        for (String str : strs) {
            int[] freq = new int[26];

            for (char ch : str.toCharArray()) {
                freq[ch - 97]++;
            }

            String key = Arrays.toString(freq);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
}