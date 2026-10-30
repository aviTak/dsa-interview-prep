import java.util.*;

class Main {
    public static void main(String args[]) {
        int intervals[][] = {{1,5},{2,6},{8,9}};
        int N = intervals.length;
        int start[] = new int[N],
            end[] = new int[N];

        for (int i = 0; i < N; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }

        Arrays.sort(start);
        Arrays.sort(end);

        int rooms = 0, j = 0;

        for (int i = 0; i < N; i++) {
            if (start[i] >= end[j]) {
                j++;
            } else {
                rooms++;
            }
        }

        System.out.println(rooms);
    }
}