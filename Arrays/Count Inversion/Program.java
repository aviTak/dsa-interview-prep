public class Program
{
    public static void main(String[] args) {
		int arr[] = {3, 8, 6, 4, 2, 1};
        System.out.println(mergeSort(arr));
	}
    
    public static int mergeSort(int arr[]) {
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static int mergeSort(int arr[], int low, int high) {
        if(low >= high)
            return 0;

        int mid = (low + high) / 2;

        return mergeSort(arr, low, mid) + mergeSort(arr, mid + 1, high) + merge(arr, low, high, mid);
    }

    public static int merge(int arr[], int low, int high, int mid) {
        int temp[] = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0, count = 0;

        while(i <= mid && j <= high) {
            if(arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += mid - i + 1;
            }
        }

        while(i <= mid)
            temp[k++] = arr[i++];

        while(j <= high)
            temp[k++] = arr[j++];

        k = 0;
        for(int z = low; z <= high; z++)
            arr[z] = temp[k++];

        return count;
    }
}
