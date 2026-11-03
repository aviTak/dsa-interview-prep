import java.util.*;

class Solution {
    public static void main(String args[]) {
        String str = "aabbccdde";

        System.out.println(countLen(str));
    }

    public static int countLen(String str) {
        if (str.isEmpty()) {
            return -1;
        }

        int maxSoFar = 1, N = str.length(), start = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            char g = str.charAt(i);

            if (map.containsKey(g)) {
                start = map.get(g) + 1;

                // map.remove(g);
            }

            map.put(g, i);
            maxSoFar = Math.max(maxSoFar, i - start + 1);
        }

        return maxSoFar;
    }
}