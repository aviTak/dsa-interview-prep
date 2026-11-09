import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] numbers = {3,0,4,2,1};
        System.out.println(missingNum(numbers));
    }

    public static int missingNum(int nums[]) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int N = nums.length,
            expSum = (N * (N + 1)) / 2,
            actSum = 0;

        for (int i = 0; i < N; i++) {
            actSum+= nums[i];
        }

        return expSum - actSum;
    }
}
