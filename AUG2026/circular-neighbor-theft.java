import java.io.*;

class Main {
    public static void main(String args[]) {
        int numbers[] = {2,7,9,3,1};

        System.out.println(robbers(numbers));
    }

    public static int robbers(int[] numbers) {
        int N = numbers.length;

        return Math.max(calc(numbers, 0, N - 2), calc(numbers, 1, N - 1));
    }

    public static int calc(int[] numbers, int start, int end) {
        int prev = 0, curr = numbers[start];

        for (int i = start + 1; i <= end; i++) {
            int t = Math.max(numbers[i] + prev, curr);

            prev = curr;
            curr = t;
        }

        return curr;
    }
}
