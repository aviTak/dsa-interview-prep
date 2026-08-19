import java.util.*;

class Duplicates {
    public static void main(String args[]) {
        int numbers[] = {10,7,0,0,9};
        // System.out.println(isDuplicate(numbers));
    }

    public static boolean isDuplicate(int numbers[]) {
        Set<Integer> s = new HashSet<>();
        int N = numbers.length;

        for (int i = 0; i < N; i++) {
            if (s.contains(numbers[i])) {
                return true;
            }
            s.add(numbers[i]);
        }

        return false;
    }
}