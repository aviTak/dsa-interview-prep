import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {0,1,0,3,2,3};

        System.out.println(longestIncreasingSubsequence(numbers));
    }

    public static int longestIncreasingSubsequence(int[] numbers) {
        int N  = numbers.length;
        
        ArrayList<Integer> sub = new ArrayList<>();
        sub.add(numbers[0])
;
        for (int i = 1; i < N; i++) {
            if (numbers[i] > sub.get(sub.size() - 1)) {
                sub.add(numbers[i]);
            } else {
                int pos = binarySearch(sub, numbers[i]);

                sub.set(pos, numbers[i]);
            }
        }

        return sub.size();
    }

    public static int binarySearch(ArrayList<Integer> sub, int K) {
        int lo = 0,
            hi = sub.size() - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (sub.get(mid) < K) {
                lo = mid + 1;
            } else {
                // Greater or equal
                hi = mid;
            }
        }

        return lo;
    }
}
