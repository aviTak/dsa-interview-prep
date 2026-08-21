import java.util.*;

class Main {
    public static void main(String args[]) {
        String str = "AAAA";
        int K = 3;

        System.out.println(longestSubstringReplacement(str, K));
    }

    public static int longestSubstringReplacement(String str, int K) {
        int maxLen = 0,
            maxFreq = 0,
            start = 0,
            N = str.length();

        int freq[] = new int[26];

        for (int i = 0; i < 26; i++) {
            freq[i] = 0;
        }

        for (int i = 0; i < N; i++) {
            char g = str.charAt(i);

            freq[g - 65]++;
            maxFreq = Math.max(maxFreq, freq[g - 65]);


            while ((i - start + 1) - maxFreq > K) {
                freq[str.charAt(start) - 65]--;
                start++;
            }

            maxLen = Math.max(maxLen, i - start + 1);
        }

        return maxLen;
    }
}