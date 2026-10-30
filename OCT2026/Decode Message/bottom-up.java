import java.util.*;

class Main {
    public static void main(String args[]) {
        String str = "1106";

        System.out.println(decode(str));
    }

    public static int decode(String str) {
        int N = str.length();
        int dp[] = new int[N + 1]; // 0 by default

        dp[0] = 1;
        dp[1] = str.charAt(1) == '0' ? 0 : 1;

        for (int i = 2; i <= N; i++) {
            // Single digits
            if (str.charAt(i - 1) != '0') {
                dp[i] = dp[i - 1];
            }

            // Double digits
            int num = Integer.parseInt(str.substring(i - 2, i));

            if (num >= 10 && num <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[N];
    }
}
