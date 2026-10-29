import java.util.*;

class Main {
    public static void main(String args[]) {
        int matrix[][] = {{1,2,3},{4,5,6},{7,8,9}};
        int M = matrix.length, N = matrix[0].length;

        // First Transpose (Swap rows with column)
        for (int i = 0; i < M; i++) {
            for (int j = i + 1; j < N; j++) {
                int t = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = t;
            }
        }

        // Second horizontal swap
        for (int i = 0; i < N; i++) {
            int start = 0, end = M - 1;
            
            while (start < end) {
                int t = matrix[i][start];
                matrix[i][start] = matrix[i][end];
                matrix[i][end] = t;

                start++;
                end--;
            }
        }

        // Print matrix
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }
}