import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] prices = {6,8,1,2,30,19};
        System.out.println(maxProfit(prices));
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int N = prices.length,
            min = Integer.MAX_VALUE,
            max = 0;

        for (int i = 0; i < N; i++) {
            min = Math.min(min, prices[i]);

            int currProfit = prices[i] - min;
            max = Math.max(max, currProfit); 
        }

        return max;
    }
}
