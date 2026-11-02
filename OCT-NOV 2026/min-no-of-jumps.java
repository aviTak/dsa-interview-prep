import java.util.*;

class Solution {
    public static void main(String args[]) {
        int arr[] = {4,1,0,0,2,3};
        System.out.println(minJumps(arr));
    }

    public static int minJumps(int[] arr) {
        int N = arr.length,
            jumps = 0,
            currReach = 0,
            maxReach = 0;

        for (int i = 0; i < N; i++) {
            maxReach = Math.max(maxReach, i + arr[i]);

            if (i == currReach) {
                if (i == maxReach) {
                    return -1;
                }

                jumps++;
                currReach = maxReach;

                if (currReach >= N - 1) {
                    return jumps;
                }
            }
        }

        return -1;
    }
}