import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[] arr = {1,-1,-1,-4,-5};

        System.out.println(longCons(arr));
    }

    public static int longCons(int[] arr) {
        int N = arr.length;

        if (N == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : arr) {
            set.add(num);
        }

        int maxCount = 0;

        for (int num : set) {
            // Check for starting elements only
            if (!set.contains(num - 1)) {
                int t = num, count = 1;

                while (set.contains(t + 1)) {
                    t = t + 1;
                    count++;
                }

                maxCount = Math.max(maxCount, count);
            }
        }
        return maxCount;
    }
}