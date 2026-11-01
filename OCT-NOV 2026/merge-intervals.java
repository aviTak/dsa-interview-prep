import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[][] intervals = {
            {1, 2},
            {3, 5},
            {6, 10},
            {11, 12},
            {13, 16}
        };

        int[] newInterval = {8, 15};

        System.out.println(merge(intervals, newInterval));
    }

    public static List<List<Integer>> merge(int[][] intervals, int[] newInterval) {
        List<List<Integer>> res = new ArrayList<>();
        int N = intervals.length, i = 0;

        // Process before ones
        while (i < N && intervals[i][1] < newInterval[0]) {
            res.add(new ArrayList<>(Arrays.asList(intervals[i][0], intervals[i][1])));
            i++;
        }

        // Since it is an overall right now, end hone se pehle current start ho rha matlab overall
        while (i < N && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
            i++;
        }

        res.add(new ArrayList<>(Arrays.asList(newInterval[0], newInterval[1])));

        // Process after ones
        while (i < N) {
            res.add(new ArrayList<>(Arrays.asList(intervals[i][0], intervals[i][1])));
            i++;
        }

        return res;
    }
}