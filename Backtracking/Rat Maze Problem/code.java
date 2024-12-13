import java.util.*;

class RatMaze {
    static ArrayList<String> arr = new ArrayList<>();
    
    public static void work(int mat[][], int i, int j, String str) {
        int NR = mat.length, NC = mat[0].length;
        
        if (i >= NR || j >= NC) {
            return;
        }
        
        if (mat[i][j] == 0) {
            return;
        }
        
        if (i == NR - 1 && j == NC - 1) {
            arr.add(str);
            return;
        }
        
        work(mat, i, j + 1, str + "R");
        work(mat, i + 1, j, str + "D");
        
        return;
    }
    
    public static void main(String args[]) {
        int mat[][] = {{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}};
        work(mat, 0, 0, "");
        
        for (String i : arr) {
            System.out.println(i);
        }
    }
}
