import java.util.*;

class Solution {
    public static void main(String args[]) {
        int numbers[] = {0,1,0,3,2,3};

        System.out.println(lis(numbers));
    }

    public static int lis(int[] arr) {
        if (arr == null) {
            return 0;
        }

        int N = arr.length;

        if (N == 0) {
            return 0;
        }

        List<Integer> sub = new ArrayList<>();
        sub.add(arr[0]);

        for (int i = 1; i < N; i++) {
            // Check if last element of sub less than arr[i] or not
            if (sub.get(sub.size() - 1) < arr[i]) {
                // Add it at the end
                sub.add(arr[i]);
            } else {
                // Otherwise, replace it at its equal or immediate greater number
                sub.set(binarySearch(sub, arr[i]), arr[i]);
            }
        }

        return sub.size();
    }

    private static int binarySearch(List<Integer> sub, int target) {
        int l = 0, r = sub.size() - 1;

        while (l < r) {
            int mid =  l + (r - l) / 2;

            if (sub.get(mid) == target) {
                return mid;
            }

            if (sub.get(mid) < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}