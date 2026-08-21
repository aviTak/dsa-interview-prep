import java.util.*;

class Main {
    public static void main(String args[]) {
        int coins[] = {3,7,4},
            target = 14;

        int res = minimumCoinsForChange(coins, target);
        System.out.println(res);
    }

    public static int minimumCoinsForChange(int[] coins, int target) {
        int N = coins.length;

        int dp[] = new int[target + 1];

        for (int i = 0; i <= target; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        dp[0] = 0;

        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < N; j++) {
                if (i - coins[j] >= 0 && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }

        return dp[target] != Integer.MAX_VALUE? dp[target] : -1;
    }
}