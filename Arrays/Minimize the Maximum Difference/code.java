class MinimizeHeight {
    public static void main(String args[]) {
        int arr[] = {3, 9, 12, 16, 20}, K = 3, N = arr.length;
        int diff = (arr[N - 1] - K) - (arr[0] + K);
        
        for (int i = 1; i < N; i++) {
            if (arr[i] - K < 0) {
                continue;
            }
            
            int min = Math.min(arr[0] + K, arr[i] - K);
            int max = Math.max(arr[N - 1] - K, arr[i - 1] + K);
            
            diff = Math.min(diff, max - min);
        }
        
        System.out.println(diff);
    }
}
