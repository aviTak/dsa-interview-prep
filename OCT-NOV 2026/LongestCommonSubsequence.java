import java.util.*;

class Solution {
    public static void main(String args[]) {
        String str1 = "xyz", str2 = "axabz";
        System.out.println(lca(str1, str2));
    }

    public static int lca(String str1, String str2) {
        int M = str1.length(),
            N = str2.length();

        int[][] dp = new int[M + 1][N + 1];
        dp[0][0] = 0;

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[M][N];
    }
}

// public class LongestCommonSubsequence {
    public static int longestCommonSubsequence(String str1, String str2) {
        if (str1.length() < str2.length()) {
            return longestCommonSubsequence(str2, str1);
        }

        int m = str1.length();
        int n = str2.length();
        int[] dp = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            int prevDiag = 0;
            char char1 = str1.charAt(i - 1);

            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (char1 == str2.charAt(j - 1)) {
                    dp[j] = prevDiag + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prevDiag = temp;
            }
        }

        return dp[n];
    }
}
