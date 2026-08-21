import java.util.*;

class Main {
    public static void main(String args[]) {
        int numbers[] = {4,9,2,1,7},
            target = 5;

        int res[] = pairSum(numbers, target);

        System.out.println(Arrays.toString(res));
    }

    public static int[] pairSum(int[] numbers, int target) {
        int N = numbers.length;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            if (map.containsKey(target - numbers[i])) {
                return new int[]{map.get(target - numbers[i]), i};
            }

            map.put(numbers[i], i);
        }

        return new int[]{};
    }
}
