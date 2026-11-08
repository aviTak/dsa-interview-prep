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

        int time = 0;

        // Create a Freq Map
        Map<Character, Integer> freqMap = new HashMap<>();
        
        for (char task : tasks) {
            freqMap.put(task, freqMap.getOrDefault(task, 0) + 1);
        }

        // Add these to max heap
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.addAll(freqMap.values());

        Queue<List<Integer>> cooling = new LinkedList<>();

        while (!pq.isEmpty() || !cooling.isEmpty()) {
            time++;

            if (!pq.isEmpty()) {
                int count = pq.poll();
                count--;

                if (count > 0) {
                    // Add to cooling
                    cooling.offer(new ArrayList<>(Arrays.asList(count, k + time)));
                }
            }

            if (!cooling.isEmpty() && cooling.peek().get(1) == time) {
                pq.offer(cooling.poll().get(0));
            }
        }

        return time;
    }
}
