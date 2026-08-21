import java.util.*;

class Main {
    public static void main(String args[]) {
        int numbers[] = {1,2,3,1};

        System.out.println(maxTheft(numbers));
    }

    public static int maxTheft(int[] numbers) {
        int N = numbers.length;

        if (N == 0) {
            return 0;
        }
        
        int dp[] = new int[N + 1];
        dp[0] = 0;
        dp[1] = numbers[0];

        for (int i = 2; i <= N; i++) {
            dp[i] = Math.max(dp[i - 1], numbers[i - 1] + dp[i - 2]);
        }

        return dp[N];
    }
}