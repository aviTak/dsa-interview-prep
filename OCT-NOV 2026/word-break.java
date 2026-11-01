import java.util.*;

class Solution {
    public static void main(String args[]) {
        String str = "greatfrontendgreat", dict[] = {"frontend","great"};
        System.out.println(isPossible(str, dict));
    }

    public static boolean isPossible(String str, String[] dict) {
        if (str == null || dict == null) {
            return false;
        }
        
        Set<String> set = new HashSet<>(Arrays.asList(dict));
        int N = str.length();

        boolean[] dp = new boolean[N + 1];
        dp[0] = true;

        for (int end = 1; end <= N; end++) {
            for (int start = 0; start < end; start++) {
                if (dp[start] && set.contains(str.substring(start, end))) {
                    dp[end] = true;
                    break;
                }
            }
        }


        return dp[N];
    }
}