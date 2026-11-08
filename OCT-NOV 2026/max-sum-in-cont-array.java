import java.util.*;

class Solution {
    public static void main(String args[]) {
        int nums[] = {-1,5,-3,9,-11};
        System.out.println(maxSum(nums));
    }

    public static int maxSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE,
            maxSoFar = 0;

        for (int num : nums) {
            maxSoFar = Math.max(num, maxSoFar + num);
            max = Math.max(max, maxSoFar);
        }

        return max;
    }
}
