import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[][] intervals = {{1, 15}, {3, 5}, {6, 8}, {8, 10}, {9, 11}};
        
        System.out.println(countRemovals(intervals));
    }

    public static int countRemovals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int lastEnd = intervals[0][1], N = intervals.length, count = 0;
        
        for (int i = 1; i < N; i++) {
            if (intervals[i][0] < lastEnd) {
                count++;
            } else {
                lastEnd = intervals[i][1];
            }
        }

        return count;
    }
}