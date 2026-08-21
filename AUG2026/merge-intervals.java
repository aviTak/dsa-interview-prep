import java.util.*;

class Main {
    public static void main(String args[]) {
        ArrayList<ArrayList<Integer>> intervals = new ArrayList<>();

        intervals.add(new ArrayList<>(Arrays.asList(1, 5)));
        intervals.add(new ArrayList<>(Arrays.asList(2, 4)));
        intervals.add(new ArrayList<>(Arrays.asList(4, 6)));
        intervals.add(new ArrayList<>(Arrays.asList(7, 8)));

        System.out.println(merge(intervals));
    }

    public static ArrayList<ArrayList<Integer>> merge(ArrayList<ArrayList<Integer>> intervals) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        int N = intervals.size();

        intervals.sort((a, b) -> a.get(0) - b.get(0)); // O(N*log(N))

        for (int i = 0; i < N; i++) {
            if (res.isEmpty() || res.get(res.size() - 1).get(1) < intervals.get(i).get(0)) {
                res.add(new ArrayList<>(intervals.get(i)));
            } else {
                int oldEnd = res.get(res.size() - 1).get(1),
                    newEnd = intervals.get(i).get(1);

                res.get(res.size() - 1).set(1, Math.max(oldEnd, newEnd));
            }
        }

        return res;
    }
}
