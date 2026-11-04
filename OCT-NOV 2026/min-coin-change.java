import java.util.*;

class Solution {
    public static void main(String args[]) {
        int coins[] = {3,7,4}, target = 14;

        System.out.println(minCoins(coins, target));
    }

    public static int minCoins(int[] coins, int target) {
        if (target == 0) {
            return 0;
        }

        if (coins.length == 0 || target < 0) {
            return -1;
        }

        // If not possible return -1
        
        int dp[] = new int[target  + 1];

        for (int i = 0; i <= target; i++) {
            dp[i] = Integer.MAX_VALUE - 1;
        }

        dp[0] = 0;

        for (int i = 1; i <= target; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        return dp[target] == Integer.MAX_VALUE - 1 ? - 1 : dp[target];
    }
}