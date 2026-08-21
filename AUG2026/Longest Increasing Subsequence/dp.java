import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {0,1,0,3,2,3};

        System.out.println(longestIncreasingSubsequence(numbers));
    }

    public static int longestIncreasingSubsequence(int[] numbers) {
        int N = numbers.length;

        int dp[] = new int[N];

        Arrays.fill(dp, 1);

        for (int i = 1; i < N; i++) {

            for (int j = 0; j < i; j++) {
                if (numbers[i] > numbers[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
        }

        int max = 0;

        for (int i = 0; i < N; i++) {
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}