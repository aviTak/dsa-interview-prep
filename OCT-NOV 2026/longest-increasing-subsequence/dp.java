import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {0,1,0,3,2,3};

        System.out.println(lis(numbers));
    }

    public static int lis(int[] arr) {
        if (arr == null) {
            return 0;
        }
        
        int N = arr.length;

        if (N == 0) {
            return 0;
        }

        int dp[] = new int[N];

        // Fill 1 to the array
        for (int i = 0; i < N; i++) {
            dp[i] = 1;
        }

        // Start from index 0 till current reach
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                // Find if the prev one was smaller to i or not
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
        }

        // Find the largest value in dp
        int max = dp[0];

        for (int i = 1; i < N; i++) {
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}

