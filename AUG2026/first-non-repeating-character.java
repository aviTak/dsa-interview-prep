import java.util.*;

class Solution {
    public static void main(String args[]) {
        String s = "geeksforgeeks";

        System.out.println(nonRepeating(s));
    }

    public static int nonRepeating(String s) {
        int N = s.length();
        int index[] = new int[26];

        Arrays.fill(index, -1);

        for (int i = 0; i < N; i++) {
            char g = s.charAt(i);

            if (index[g - 97] == -2) {
                // Skip
                continue;
            }

            if (index[g - 97] != -1) {
                // Useless
                index[g - 97] = -2;
            } else {
                index[g - 97] = i;
            }
        }

        int minIndex = Integer.MAX_VALUE;

        for (int i = 0; i < 26; i++) {
            if (index[i] < 0) {
                continue;
            }

            minIndex = Math.min(minIndex, index[i]);
        }

        return minIndex == Integer.MAX_VALUE? -1 : minIndex;
    }
}