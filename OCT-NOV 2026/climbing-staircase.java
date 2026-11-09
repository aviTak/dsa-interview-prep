import java.util.*;

class Solution {
    public static void main(String args[]) {
        int steps = 5;
        System.out.println(count(steps));
    }

    public static int count(int steps) {
        if (steps < 0) {
            return -1;
        }

        int prev = 1,
            curr = 1;

        for (int i = 2; i <= steps; i++) {
            int t = prev + curr;
            prev = curr;
            curr = t;
        }

        return curr;
    }
}
