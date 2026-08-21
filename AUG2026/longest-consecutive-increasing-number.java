import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] numbers = {1,-1,0,8,11,10,9,9};

        System.out.println(longestConsecutiveNumberSeq(numbers));
    }

    public static int longestConsecutiveNumberSeq(int[] numbers) {
        int maxLen = 0,
            N = numbers.length;

        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < N; i++) {
            set.add(numbers[i]);
        }

        for (int num : set) {
            // Check is starting point
            if (!set.contains(num - 1)) {
                int len = 1,
                    temp = num + 1;

                while (set.contains(temp)) {
                    len++;
                    temp++;
                }

                maxLen = Math.max(maxLen, len);
            }
        }

        return maxLen;
    }
}