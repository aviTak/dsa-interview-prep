import java.util.*;

class Main {
    public static void main(String args[]) {
        int matrix[][] = {{7,7,0,7},{4,3,7,2},{9,8,6,0},{6,1,3,6}};
        int M = matrix.length, N = matrix[0].length;
        ArrayList<Integer> res = new ArrayList<>();

        int left = 0, right = N - 1, top = 0, bottom = M - 1;

        while (top <= bottom && left <= right) {
            // Left to right
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;

            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    res.add(matrix[bottom][i]);
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }

        System.out.println(res);
    }
}