import java.util.*;

class Main {
    public static void main(String args[]) {
        int numbers[] = {2,3,4,0,1}, target = 0;

        System.out.println(findIndex(numbers, target));
    }

    public static int findIndex(int[] numbers, int target) {
        int N = numbers.length,
            lo = 0,
            hi = N - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (numbers[mid] == target) {
                return mid;
            }

            // Is left sorted
            if (numbers[lo] <= numbers[mid]) {
                // Target inside left range
                if (numbers[lo] <= target && numbers[mid] >= target) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                // Right is sorted
                // Target inside right range
                if (numbers[mid] <= target && numbers[hi] > target) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }

        return -1;
    }
}