import java.util.*;

class Main {
    public static void main(String args[]) {
        int numbers[] = {0,7,1,9},
            target = 7;

        int res[] = pairSum(numbers, target);

        System.out.println(Arrays.toString(res));
    }

    public static int[] pairSum(int[] numbers, int target) {
        int N = numbers.length;

        Arrays.sort(numbers);

        // 1, 2, 4, 7, 9 | 5

        int i = 0, j = N - 1;

        while (i < j) {
            int sum = numbers[i] + numbers[j];

            if (sum == target) {
                return new int[]{numbers[i], numbers[j]};
            }

            if (sum > target) {
                j--;
            } else {
                i++;
            }

        }

        return new int[]{};
    }
}