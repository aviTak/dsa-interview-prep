import java.util.*;

class Main {
    public static void main(String args[]) {
        String s = "racecar";

        System.out.println(longestPalindrome(s));
    }

    public static int longestPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int N = s.length();

        StringBuilder sb = new StringBuilder();
        sb.append("^#");

        for (int i = 0; i < N; i++) {
            sb.append(s.charAt(i));
            sb.append("#");
        }

        sb.append("$");

        String st = sb.toString();
        N = st.length();

        int maxLen = 0, maxCenterIndex = 0, right = 0, center = 0;
        int p[] = new int[N];

        for (int i = 1; i < N - 1; i++) {
            int mirror = 2 * center - i;

            if (i < right) {
                p[i] = Math.min(p[mirror], right - i);
            }

            while (st.charAt(i - p[i] - 1) == st.charAt(i + p[i] + 1)) {
                p[i]++;
            }

            if (p[i] + i > right) {
                center = i;
                right = i + p[i];
            }

            if (p[i] > maxLen) {
                maxLen = p[i];
                maxCenterIndex = i;
            }
        }

        int start = (maxCenterIndex - maxLen) / 2,
            end = start + maxLen;
            
        System.out.println(s.substring(start, end));

        return maxLen;
    }
}