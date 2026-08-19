import java.util.*;

class Kadane {
    public static void main(String args[]) {
        int numbers[] = {1, 2, 3, 4};
        System.out.println(max(numbers));
    }
    public static int max(int numbers[]) {
        int N = numbers.length, currMax = 0, totalMax = Integer.MIN_VALUE;

        for (int i = 0; i < N; i++) {
            currMax = Math.max(numbers[i], numbers[i] + currMax);
            totalMax = Math.max(currMax, totalMax);
        }

        return totalMax;
    }
}
