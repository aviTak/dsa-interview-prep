import java.util.*;
import java.io.*;

class Zero12 {
    public static void main(String args[]) throws IOException {
        BufferedReader x = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("N = ");
        int N = Integer.parseInt(x.readLine());
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0; i<N; i++) {
            arr.add(Integer.parseInt(x.readLine()));
        }
        
        int i = 0, j = 0, k = N - 1, t;
        while(i <= k) {
            switch(arr.get(i)) {
                case 0:
                    t = arr.get(i);
                    arr.set(i, arr.get(j));
                    arr.set(j, t);
                    
                    i++; j++;
                    break;
                case 1:
                    i++;
                    break;
                case 2:
                    t = arr.get(k);
                    arr.set(k, arr.get(i));
                    arr.set(i, t);
                    
                    k--;
                    break;
                default:
                    System.out.println("Invalid array");
            }
        }
        
        System.out.println(arr);
    }
}
