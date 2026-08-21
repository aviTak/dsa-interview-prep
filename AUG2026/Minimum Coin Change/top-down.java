import java.util.*;

class Main {
    public static void main(String args[]) {
        int coins[] = {3,7,4},
            target = 14;

        int res = minimumCoinsForChange(coins, target);
        System.out.println(res);
    }

    public static int minimumCoinsForChange(int[] coins, int target) {
        if (target == 0) {
            return 0;
        }

        int N = coins.length,
            res = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            if (target - coins[i] >= 0) {
                int t = minimumCoinsForChange(coins, target - coins[i]);

                if (t != -1) {
                    res = Math.min(res, t + 1);
                }
            }
        }

        return res != Integer.MAX_VALUE? res : -1;
    }
}