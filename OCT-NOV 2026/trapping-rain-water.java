import java.util.*;

class Main {
    public static void main(String args[]) {
        int[] arr = {2, 1, 5, 3, 1, 0, 4};
        System.out.println(maxWater(arr));
    }

    public static int maxWater(int[] arr) {
        int res = 0, N = arr.length;

        if (N <= 1) {
            return 0;
        }

        int maxL = arr[0], maxR = arr[N - 1], i = 1, j = N - 2;

        while (i <= j) {
            if (maxL <= maxR) {
                // Calculate from left
                res += Math.max(0, maxL - arr[i]);
                maxL = Math.max(maxL, arr[i]);
                i++;
            } else {
                res += Math.max(0, maxR - arr[j]);
                maxR = Math.max(maxR, arr[j]);
                j--;
            }
        }

        return res;
    }
}