import java.util.*;

class Main {
    public static void main(String args[]) {
        String str = "No 'x' in Nixon";

        System.out.println(isStringPalindrome(str));
    }

    public static boolean isStringPalindrome(String str) {
        int N = str.length(),
            i = 0,
            j = N - 1;

        str = str.toLowerCase();

        while (i < j) {
            char q = str.charAt(i),
                w = str.charAt(j);

            boolean skip = false;

            if (q < 97 || q > 122) {
                i++;
                skip = true;
            }

            if (w < 97 || w > 122) {
                j++;
                skip = true;
            }

            if (!skip && q != w) {
                return false;
            }

            if (!skip) {
                i++;
                j--;
            }
        }

        return true;
    }
}