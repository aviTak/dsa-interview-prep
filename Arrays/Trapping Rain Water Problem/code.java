public class Program
{
    public static void main(String[] args) {
		int arr[] = {3, 1, 2, 4, 0, 1, 3, 2};
        int N = arr.length;
        int left[] = new int[N];
        int right[] = new int[N];
        int m1 = 0, m2 = 0, area = 0;

        for(int i = 0; i < N; i++) {
            if(arr[i] > m1) {
                m1 = arr[i];
            }
            left[i] = m1;

            if(arr[N - i - 1] > m2) {
                m2 = arr[N - i - 1];
            }
            right[N - i - 1] = m2;
        }

        for(int i = 0; i < N; i++) {
            area += Math.min(left[i], right[i]) - arr[i];
            System.out.println(area);
        }

        System.out.println(area);
	}
}
