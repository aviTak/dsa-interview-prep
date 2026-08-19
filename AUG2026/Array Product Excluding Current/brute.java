import java.util.*;

class Product {
    public static void main(String args[]) {
        int numbers[] = {2,0,3};
        System.out.println(Arrays.toString(calcProduct(numbers)));
    }

    public static int[] calcProduct(int numbers[]) {
        int N = numbers.length,
            res[] = new int[N],
            prefix[] = new int[N],
            suffix[] = new int[N];

        prefix[0] = 1;
        for (int i = 1; i < N; i++) {
            prefix[i] = prefix[i - 1] * numbers[i - 1];
        }

        suffix[N - 1] = 1;
        for (int i = N - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * numbers[i + 1];
        }

        for (int i = 0; i < N; i++) {
            res[i] = suffix[i] * prefix[i];
        }

        return res;
    }
}