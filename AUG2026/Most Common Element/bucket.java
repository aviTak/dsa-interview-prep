import java.util.*;

class Bucket {
    public static void main(String args[]) {
        int numbers[] = {4,4,4,4,6,6,5,5,5}, K = 2;
        System.out.println(common(numbers, K));
    }

    public static ArrayList<Integer> common(int numbers[], int K) {
        ArrayList<Integer> res = new ArrayList<>();

        HashMap<Integer, Integer> map = new HashMap<>();
        
        int N = numbers.length;

        ArrayList<ArrayList<Integer>> bucket = new ArrayList<>(N + 1);

        for (int i = 0; i <= N; i++) {
            bucket.add(new ArrayList<>());
        }

        for (int i = 0; i < N; i++) {
            map.put(numbers[i], map.getOrDefault(numbers[i], 0) + 1);
        }

        for (Map.Entry<Integer, Integer> et : map.entrySet()) {
            int num = et.getKey(), freq = et.getValue();

            bucket.get(freq).add(num);
        }

        for (int i = N; i >= 0 && K > 0; i--) {
            for (int num : bucket.get(i)) {
                if (K > 0) {
                    res.add(num);
                    K--;
                }
            }
        }

        return res;
    }
}