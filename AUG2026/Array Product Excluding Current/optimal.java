import java.util.*;

class Product {
    public static void main(String args[]) {
        int numbers[] = {1,2,3};

        System.out.println(Arrays.toString(calcProduct(numbers)));
    }

    public static int[] calcProduct(int numbers[]) {
        int N = numbers.length,
            res[] = new int[N];

        res[0] = 1;
        for (int i = 1; i < N; i++) {
            res[i] = res[i - 1] * numbers[i - 1];
        }

        int right = 1;
        for (int i = N - 1; i >= 0; i--) {
            res[i]*= right;
            right*= numbers[i];
        }

        return res;
    }
}