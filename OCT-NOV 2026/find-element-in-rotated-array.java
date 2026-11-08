import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {2,3,4,0,1}, target = 0;

        System.out.println(find(numbers, target));
    }

    public static int find(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int N = nums.length,
            i = 0,
            j = N - 1;

        while (i <= j) {
            int mid = i + (j - i) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Check which side is sorted
            if (nums[mid] >= nums[i]) {
                // Left is sorted
                if (nums[i] <= target && target < nums[mid]) {
                    j = mid - 1;
                } else {
                    i = mid + 1;
                }
            } else {
                // Right is sorted
                if (nums[mid] < target && target <= nums[j]) {
                    i = mid + 1;
                } else {
                    j = mid - 1;
                }
            }
        }

        return -1;
    }
}
