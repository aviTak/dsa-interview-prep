import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] nums = {1,2,3,1};

        System.out.println(maxRobber(nums));
    }

    public static int maxRobber(int[] nums) {
        int N = nums.length;

        if (N == 0) return 0;
        if (N == 1) return nums[0];

        return Math.max(
            maxRobber(nums, 0, N - 2),
            maxRobber(nums, 1, N - 1)
        );
    }

    private static int maxRobber(int[] arr, int i, int j) {
        int prev = 0;
        int curr = arr[i];
        i++;

        while (i <= j) {
            int temp = Math.max(arr[i] + prev, curr);

            prev = curr;
            curr = temp;

            i++;
        }

        return curr;
    }
}