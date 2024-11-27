import java.util.*;

class ActivitySelection {
    static class Pair {
        int start;
        int end;
        
        Pair(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    
    static ArrayList<Pair> activitySelection(int start[], int end[], int N) {
        ArrayList<Pair> res = new ArrayList<>();
        PriorityQueue<Pair> pq = new PriorityQueue<>((p1, p2) -> p1.end - p2.end);
        
        for (int i = 0; i < N; i++) {
            pq.add(new Pair(start[i], end[i]));
        }
        
        Pair p = pq.poll();
        int lastSelectedStart = p.start, lastSelectedEnd = p.end;
        
        res.add(new Pair(lastSelectedStart, lastSelectedEnd));
        
        while (!pq.isEmpty()) {
            Pair it =  pq.poll();
            
            if (lastSelectedEnd <= it.start) {
                lastSelectedStart = it.start;
                lastSelectedEnd = it.end;
                res.add(new Pair(lastSelectedStart, lastSelectedEnd));
            }
        }
        
        for(int i = 0; i < N; i++) {
            pq.add(new Pair(start[i], end[i]));
        }
        
        return res;
    }
    
    public static void main(String args[]) {
        int start[] = {1, 3, 0, 5, 8, 5}, end[] = {2, 4, 6, 7, 9, 9}, N = start.length;
        ArrayList<Pair> res = activitySelection(start, end, N);
        
        for(Pair itr : res) {
            System.out.println("Starts at: " + itr.start + "; Ends at: " + itr.end);
        }
    }
}
