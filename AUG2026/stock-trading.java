import java.util.*;

class Main {
    public static void main(String args[]) {
        int[] prices = {4,3,2,1};
        int res = optimalStockTrading(prices);

        System.out.println(res);
    }

    public static int optimalStockTrading(int[] prices) {
        int N = prices.length;

        if (N == 0) {
            return 0;
        }

        int lowest = prices[0],
            totMax = 0;

        for (int i = 1; i < N; i++) {
            int curr = prices[i] - lowest;

            totMax = Math.max(totMax, curr);
            lowest = Math.min(lowest, prices[i]);
        }

        return totMax;
    }
}