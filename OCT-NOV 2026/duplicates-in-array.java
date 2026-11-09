import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] numbers = {10,7,0,0,9};
        System.out.println(hasDup(numbers));
    }

    public static boolean hasDup(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }

        return false;
    }
}
