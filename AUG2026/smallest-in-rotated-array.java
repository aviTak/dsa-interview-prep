import java.util.*;

class Main {
    public static void main(String args[]) {
        int numbers[] = {3,4,1,2};
            
        System.out.println(findSmallest(numbers));
    }

    public static int findSmallest(int[] numbers) {
        int N = numbers.length,
            lo = 0,
            hi = N - 1,
            res = Integer.MAX_VALUE;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            // Is left sorted
            if (numbers[lo] <= numbers[mid]) {
                res = Math.min(res, numbers[lo]);
                lo = mid + 1;
            } else {
                res = Math.min(res, numbers[mid]);
                hi = mid - 1;
            }
        }

        return res;
    }
}