class MissingNumber {
    public static void main(String args[]) {
        int numbers[] = {3,0,4,2,1};
        System.out.println(missingNumber(numbers));
    }

    public static int missingNumber(int numbers[]) {
        int N = numbers.length, expSum = 0, currSum = 0;

        for (int i = 0; i < N; i++) {
            expSum+= i;
            currSum+= numbers[i];
        }

        expSum+= N;

        return expSum - currSum;
    }
}