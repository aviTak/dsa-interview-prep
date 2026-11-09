import java.util.*;

class Solution {
    public static void main(String args[]) {
        String  str1 = "abcd", str2 = "dcba";
        System.out.println(isAnagram(str1, str2));
    }

    public static boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }

        int[] count = new int[26];
        str1.toLowerCase();
        str2.toLowerCase();
        
        int N = str1.length();

        for (int i = 0; i < N; i++) {
            char g1 = str1.charAt(i),
                g2 = str2.charAt(i);

            count[g1 - 97]++;
            count[g2 - 97]--;
        }

        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) {
                return false;
            }
        }

        return true;
    }
}
