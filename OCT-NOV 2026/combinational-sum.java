import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {1,2,3}, target = 4;

        System.out.println(combSum(numbers, target));
    }

    public static int combSum(int[] arr, int target) {
        int dp[] = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int num : arr) {
                if (i - num >= 0) {
                    dp[i] += dp[i - num];
                }
            }
        }

        return dp[target];
    }
}