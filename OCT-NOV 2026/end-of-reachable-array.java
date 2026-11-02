import java.util.*;

class Solution {
    public static  void main(String args[]) {
        int arr[] = {2, 3, 1, 1, 4};

        System.out.println(isReachable(arr));
    }

    public static boolean isReachable(int[] arr) {
        int N = arr.length;

        if (N <= 1) {
            return true;
        }

        int maxSoFar = Integer.MIN_VALUE;

        for (int i = 0; i < N; i++) {
            if (i > maxSoFar) {
                return false;
            }

            maxSoFar = Math.max(maxSoFar, arr[i] + i);

            if (maxSoFar >= N - 1) {
                return true;
            }
        }

        return false;
    }
}