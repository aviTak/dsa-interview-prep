import java.util.*;

class Triplet {
    public static void main(String args[]) {
        int nums[] = {-1,0,1,2,-1,-4};
        System.out.println(triplet(nums));
    }

    public static ArrayList<ArrayList<Integer>> triplet(int[] nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int N = nums.length;

        // Sort the array
        Arrays.sort(nums);

        // Outer loop
        for (int i = 0; i < N; i++) {
            // Skip same ones
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1, k = N - 1;

            while (j < k) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k])));

                    /* Skip duplicates */

                    do {
                        j++;
                    } while (j < k && nums[j] == nums[j - 1]);

                    do {
                        k--;
                    } while (j < k && nums[k] == nums[k + 1]);
                } else if (nums[i] + nums[j] + nums[k] < 0) {
                    j++;
                } else {
                    k--;
                }
            } 
        }


        return res;
    }
}