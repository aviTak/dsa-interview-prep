import java.util.*;

class Main {
    public static void main(String args[]) {
        String str1 = "hello",
            str2 = "bello";

        System.out.println(isStringAnagram(str1, str2));
    }

    public static boolean isStringAnagram(String str1, String str2) {
        str1.toLowerCase();
        str2.toLowerCase();

        int freq[] = new int[26];

        for (int i = 0; i < 26; i++) {
            freq[i] = 0;
        }

        for (int i = 0; i < str1.length(); i++) {
            char g = str1.charAt(i);

            freq[g - 97]++;
        }

        for (int i = 0; i < str2.length(); i++) {
            char g = str2.charAt(i);

            freq[g - 97]--;
        }

        for (int i = 0; i < 26; i++) {
            if (freq[i] != 0) {
                return false;
            }
        }

        return true;
    }
}