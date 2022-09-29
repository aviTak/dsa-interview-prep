public class Program
{
    public static void main(String[] args) {
		int[] arr = {1, 0, 1, 0, 1, 0, 1, 0};
        int p = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == 0) {
                int t = arr[i];
                arr[i] = arr[p];
                arr[p] = t;
                p++;
            }
        }

        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
	}
}
