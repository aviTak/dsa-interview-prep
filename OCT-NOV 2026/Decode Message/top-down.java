import java.util.*;

class Main {
    public static void main(String args[]) {
        String str = "225";
        System.out.println(decode(str));
    }

    public static int decode(String str) {
        int memo[] = new int[str.length()];
        Arrays.fill(memo, -1);

        return decode(str, 0, memo);
    }

    public static int decode(String str, int index, int[] memo) {
        if (str.length() == index) {
            // Valid
            return 1;
        }

        if (str.charAt(index) == '0') {
            return 0;
        }

        if (memo[index] != -1) {
            return memo[index];
        }

        int res = decode(str, index + 1, memo);

        if (index + 1 < str.length()) {
            int num = Integer.parseInt(str.substring(index, index + 2));

            if (num >= 10 && num <= 26) {
                res += decode(str, index + 2, memo);
            }
        }

        return memo[index] = res;
    } 
}