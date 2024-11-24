class MinimumJumps {
    public static void main(String args[]) {
        int arr[] = {14,4,18,1,15}, N = arr.length;
        int maxValue = 0, maxIndex = 0, count = 1, travel = arr[0];
        
        if (arr[0] >= N) {
            System.out.println(1);
            System.exit(0);
        }
        
        if (arr[0] == 0) {
            System.out.println(-1);
            System.exit(0);
        }
        
        for(int i = 1; i < N - 1; i++) {
            if (arr[i] + i > maxValue + maxIndex) {
                maxValue = arr[i];
                maxIndex = i;
            }
            
            if (i == travel) {
                if (maxValue == 0) {
                    System.out.println(-1);
                    System.exit(0);
                }
                
                count++;
                travel = maxValue + maxIndex;
                maxValue = 0;
                maxIndex = 0;
            }
        }
        
        System.out.println(count);
    }
}
