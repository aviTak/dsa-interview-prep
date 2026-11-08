import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] nums = {3, 4, 1, 2};

        System.out.println(smallest(nums));
    }

    public static int smallest(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int N = nums.length,
            i = 0,
            j = N - 1;

        while (i < j) {
            int mid = i + (j - i) / 2;

            if (nums[mid] > nums[j]) {
                i = mid + 1;
            } else {
                j = mid;
            }
        }

        return nums[i];
    }
}
