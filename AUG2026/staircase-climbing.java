import java.util.*;

class Staircase {
    public static void main(String args[]) {
        int steps = 5;

        System.out.println(combinations(steps));
    }

    public static int combinations(int steps) {
        if (steps == 0) {
            return 1;
        }

        int dp[] = new int[steps + 1];

        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= steps; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[steps];
    }
}