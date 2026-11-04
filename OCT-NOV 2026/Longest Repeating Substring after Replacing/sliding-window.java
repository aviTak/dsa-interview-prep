import java.util.*;

class Solution {
    public static void main(String args[]) {
        String str = "aabaa";
        int k = 2;

        System.out.println(lrs(str, k));
    }

    public static int lrs(String str, int k) {
        if (str.isEmpty()) {
            return 0;
        }

        int largest = Integer.MIN_VALUE,
            start = 0,
            maxFreq = 0,
            N = str.length();

        int[] ch = new int[26];

        for (int i = 0; i < N; i++) {
            char g = str.charAt(i);

            ch[g - 97]++;
            maxFreq = Math.max(maxFreq, ch[g - 97]);

            if (i - start + 1 - maxFreq > k) {
                // Invalid
                ch[str.charAt(start) - 97]--;
                start++;
            }

            largest = Math.max(largest, i - start + 1);
        }

        return largest;
    }
}