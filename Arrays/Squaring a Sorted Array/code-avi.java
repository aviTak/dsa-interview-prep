import java.util.*;

public class Hello {
    public static void main(String args[]) {
        int arr[] = {-2, -1, 0, 2, 3};
        int res[] = new int[arr.length];
        int i = 0, j = arr.length - 1, k = 0;

        while(i <= j) {
            int f = arr[i];
            int l = arr[j];

            if(f*f > l*l) {
                res[k++] = f*f;
                i++;
            } else {
                res[k++] = l*l;
                j--;
            }
        }

        for(int w = 0; w < res.length; w++) {
            System.out.print(res[w] + " ");
        }
        System.out.println();

        for(int q = 0; q < res.length / 2; q++) {
            int t = res[q];
            res[q] = res[res.length - 1 - q];
            res[res.length - 1 - q] = t;
            
        }

        for(int w = 0; w < res.length; w++) {
            System.out.print(res[w] + " ");
        }
        System.out.println();
    }
}
