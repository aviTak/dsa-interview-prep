import java.util.*;

class Main {
    public static void main(String args[]) {
        int intervals[][] = {{1,2},{3,4},{5,6},{7,8}},
            newInterval[] = {2,5};
            
        System.out.println(mergeNewInterval(intervals, newInterval));
    }

    public static ArrayList<ArrayList<Integer>> mergeNewInterval(int[][] intervals, int[] newInterval) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        int N = intervals.length, i = 0;

        while (i < N && intervals[i][1] < newInterval[0]) {
            res.add(new ArrayList<>(
                Arrays.asList(
                    intervals[i][0],
                    intervals[i][1]
                )
            ));
            i++;
        }

        // Overlapping
        while (i < N && newInterval[1] >= intervals[i][0]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }

        // Now add new interval
        res.add(new ArrayList<>(Arrays.asList(
            newInterval[0],
            newInterval[1]
        )));

        // Remaining
        while (i < N) {
            res.add(new ArrayList<>(Arrays.asList(
                intervals[i][0],
                intervals[i][1]
            )));
            i++;
        }

        return res;
    }
}