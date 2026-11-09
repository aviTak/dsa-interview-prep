import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {4,9,2,1,7}, target = 5;

        System.out.println(pairSum(numbers, target));
    }

    public static List<Integer> pairSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        Map<Integer, Integer> map = new HashMap<>();
        int N = nums.length;

        for (int i = 0; i < N; i++) {
            if (map.containsKey(k - nums[i])) {
                return new ArrayList<>(List.of(map.get(k - nums[i]), i));
            }
            map.put(nums[i], i);
        }

        return new ArrayList<>();
    }
}
