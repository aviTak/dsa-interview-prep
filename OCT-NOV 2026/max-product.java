import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] numbers = {1,2,0,-1,8,-4};
        System.out.println(maxProduct(numbers));
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = nums[0],
            maxSoFar = nums[0],
            minSoFar = nums[0],
            N = nums.length;

        for (int i = 1; i < N; i++) {
            int num = nums[i];

            if (num < 0) {
                // Reverse
                int t = maxSoFar;
                maxSoFar = minSoFar;
                minSoFar = t;
            }

            maxSoFar = Math.max(num, num * maxSoFar);
            minSoFar = Math.min(num, num * minSoFar);
            max = Math.max(max, maxSoFar);
        }

        return max;
    }
}
