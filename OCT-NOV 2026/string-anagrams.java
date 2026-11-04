import java.util.*;

class Solution {
    public static void main(String args[]) {
        String strs[] = {"abc","bca","cab","xyz","zyx"};

        System.out.println(groups(strs));
    }

    public static List<List<String>> groups(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            // Find occurrence
            int ch[] = new int[26];

            int N = str.length();

            for (int i = 0; i < N; i++) {
                ch[str.charAt(i) - 97]++;
            }

            String key = "";
            for (int i = 0 ; i < 26; i++) {
                key += "#";
                key += ch[i];
            }

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        res.addAll(map.values());
        return res;
    }
}