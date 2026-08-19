import java.util.*;

class MaxProduct {
    public static void main(String args[]) {
        int numbers[] = {1,2,0,-1,8,-4};
        System.out.println(maxProduct(numbers));
    }

    public static int maxProduct(int numbers[]) {
        int N = numbers.length;

        if (N == 0) {
            return 0;
        }

        int maxSoFar = numbers[0],
            minSoFar = numbers[0],
            res = numbers[0];

        for (int i = 1; i < N; i++) {
            int t = Math.max(numbers[i], Math.max(numbers[i] * minSoFar, numbers[i] * maxSoFar));
            minSoFar = Math.min(numbers[i], Math.min(numbers[i] * minSoFar, numbers[i] * maxSoFar));
            maxSoFar = t;
            res = Math.max(res, Math.max(maxSoFar, minSoFar));
        }

        return res;
    }
}