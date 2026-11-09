import java.util.*;

class Solution {
    public static void main(String args[]) {
        String str = "No 'x' in Nixon";
        System.out.println(isPalindrome(str));
    }

    public static boolean isPalindrome(String str) {
        int N = str.length(),
            i = 0,
            j = N - 1;

        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(str.charAt(i))) {
                i++;
            }

            while (i < j && !Character.isLetterOrDigit(str.charAt(j))) {
                j--;
            }

            if (Character.toLowerCase(str.charAt(i)) != Character.toLowerCase(str.charAt(j))) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }
}
