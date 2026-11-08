import java.util.*;

class Solution {
    public static void main(String args[]) {
        char tasks[] = {'A','A','A','B','B','B'};
        int k = 2;

        System.out.println(tasksScheduler(tasks, k));
    }

    public static int tasksScheduler(char[] tasks, int k) {
        if (tasks == null || tasks.length == 0 || k < 0) {
            return -1;
        }

        int N = tasks.length;

        if (k == 0) {
            return N;
        }

        // Find the max freq count and the no. of such elements
        int maxFreq = 0, count = 0;
        int counter[] = new int[26];

        for (char task : tasks) {
            counter[task - 65]++;

            if (maxFreq == counter[task - 65]) {
                count++;
            } else if (counter[task - 65] > maxFreq) {
                maxFreq = counter[task - 65];
                count = 1;
            }
        }
        
        int partitions = maxFreq - 1,
            gaps = k - (count - 1),
            emptySlots = partitions * gaps,
            availableTasks = N - maxFreq * count,
            idles = Math.max(0, emptySlots - availableTasks);

        return N + idles;
    }
}
