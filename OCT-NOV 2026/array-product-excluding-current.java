import java.util.*;

class Solution {
    public static void main(String args[]) {
        int nums[] = {1, 2, 3},
            res[] = product(nums);

        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    public static int[] product(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int N = nums.length,
            res[] = new int[N];

        int prefix = 1;

        for (int i = 0; i < N; i++) {
            res[i] = prefix;
            prefix *= nums[i];
        }

        int suffix = 1;

        for (int i = N - 1; i >= 0; i--) {
            res[i] *= suffix;
            suffix *= nums[i];
        }

        return res;
    }
}
