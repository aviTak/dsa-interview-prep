import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[][] intervals = {{1,5},{5,10},{10,15}};

        System.out.println(isPossible(intervals));
    }

    public static boolean isPossible(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return false;
        }

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int N = intervals.length;

        for (int i = 0; i < N - 1; i++) {
            if (intervals[i][1] > intervals[i + 1][0]) {
                return false;
            }
        }

        return true;
    }
}
