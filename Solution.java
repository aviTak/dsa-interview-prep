public import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

// Java program to implement in-built pair classes
import javafx.util.Pair;

public class Solution {

    static int addNumbers(int a, int b) {
        return a+b; 
    }

    public static void main(String[] args) {
         char[] tasks = {'A','A','A','B','B','B', 'C','C','C', 'D', 'D', 'E'};
        int n = 2;

        int result = leastInterval(tasks, n);

        System.out.println("Minimum intervals required: " + result);
    }
    
    public static int leastInterval(char tasks[], int n) {
        HashMap<Character, Pair<Integer, Integer>> map = new HashMap<>();
        
        int len = tasks.length;
        
        for (int i = 0; i < len; i++) {
            char g = tasks[i];
            
            if (map.containsKey(g)) {
                Pair<Integer, Integer> pr = map.get(g);
                
                int ky = pr.getKey();
                
                // System.out.println(g + "" + ky);
                
                map.put(g, new Pair<>(ky + 1, -1));
            } else {
                map.put(g, new Pair<>(1, -1));
            }
        }
        
        int count = 0;
        // boolean isOver = false;
        
        while (true) {
            boolean isOver = true;
            boolean isUsed = false;
            // 1. Iteraate the unique characters
            // if usuable add, else continue to next,
            // none --> idle
            // 2. if in between all have remaining 0, 
             //   exit the loop --> done
             
             Iterator hmIterator = map.entrySet().iterator();
             
             while (hmIterator.hasNext()) {

                Map.Entry mapElement
                    = (Map.Entry)hmIterator.next();
                    
                // Pair<Integer, Integer> pr = mapElement.get();
                    
                Pair<Integer, Integer> pr = (Pair<Integer, Integer>)mapElement.getValue();

                int remaining = pr.getKey();
                int index = pr.getValue();
                
                if (remaining > 0) {
                    isOver = false;
                    
                    
                    if (index == -1 || count - index > n) {
                        map.put((char)mapElement.getKey(), new Pair<Integer, Integer>(remaining - 1, count));
                        
                        isUsed = true;
                        
                        count++;
                        break;
                    }
                }
                
                
        }
             
             if (isOver) {
                break;
             }
             
             if (!isUsed) {
                count++;
             }
        }
        
        return count;
        
    }
    
}
// Your old code in javascript has been preserved below.
// import java.io.*;
// import java.util.*;
// import java.text.*;
// import java.math.*;
// import java.util.regex.*;

// public class Solution {

//     static int addNumbers(int a, int b) {
//         return a+b; 
//     }

//     public static void main(String[] args) {
//         Scanner in = new Scanner(System.in);
//         int a;
//         a = in.nextInt();
//         int b;
//         b = in.nextInt();
//         int sum;

//         sum = addNumbers(a, b);
//         System.out.println(sum);
//     }
// }
 {
    
}
