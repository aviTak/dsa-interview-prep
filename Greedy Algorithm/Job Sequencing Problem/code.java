import java.util.*;

class JobSequencingGreedy {
    static class Job {
        int id;
        int profit;
        int deadline;
        
        public Job(int id, int profit, int deadline) {
            this.id = id;
            this.profit = profit;
            this.deadline = deadline;
        }
    }
    
    public static void main(String args[]) {
         ArrayList<Job> jobs = new ArrayList<>();
         jobs.add(new Job(1, 25, 4));
         jobs.add(new Job(2, 12, 1));
         jobs.add(new Job(3, 5, 2));
         jobs.add(new Job(4, 35, 3));
         jobs.add(new Job(5, 30, 4));
         jobs.add(new Job(6, 20, 2));
         jobs.add(new Job(7, 15, 3));
         
         Collections.sort(jobs, (a, b) -> b.profit - a.profit);
         
         for (Job i : jobs) {
             System.out.println("Id: " + i.id + " Profit: " + i.profit + " Deadline: " + i.deadline);
         }
         
         
         int N = jobs.size();
         int slots[] = new int[N];
         
         for (int i = 0; i < N; i++) {
             Job t = jobs.get(i);
             int lastSlot = t.deadline - 1;
             
             for (int j = lastSlot; j >= 0; j--) {
                 if (slots[j] == 0) {
                     slots[j] = t.id;
                     break;
                 }
             }
         }
         
         for (int i = 0; i < N; i++) {
             System.out.println(slots[i]);
         }
    }
}
