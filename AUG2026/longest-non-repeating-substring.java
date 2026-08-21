import java.util.*;

class Main {
    public static void main(String args[]) {
        String str = "aabbccdde";

        System.out.println(longestUniqueSubstring(str));
    }

    public static int longestUniqueSubstring(String str) {
        int N = str.length();

        int maxLen = 0, start = 0;

        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            char g = str.charAt(i);

            if (map.containsKey(g)) {
                int id = map.get(g);

                if (start <= id) {
                    start = id + 1;
                }
            }

            int t = i - start + 1;
            maxLen = Math.max(maxLen, t);
            map.put(g, i);
        }

        return maxLen;
    }
}