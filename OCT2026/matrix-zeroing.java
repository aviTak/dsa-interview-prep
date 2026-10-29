import java.util.*;

class Main {
    public static void main(String args[]) {
        int matrix[][] = {{1,2,0},{4,0,6},{7,8,9}};
        int M = matrix.length, N = matrix[0].length;

        boolean isFirstRowZero = false, isFirstColumnZero = false;

        for (int i = 0; i  < N; i++) {
            if (matrix[0][i] == 0) {
                isFirstRowZero = true;
                break;
            }
        }

        for (int i = 0; i < M; i++) {
            if (matrix[i][0] == 0) {
                isFirstColumnZero = true;
                break;
            }
        }

        // Check inner matrix if it has zero
        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        // Start zeroing inner matrix based on the markers
        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Zero the first row if required
        if (isFirstRowZero) {
            for (int i = 0; i <  N; i++) {
                matrix[0][i] = 0;       
            }
        }

        // Zero the first column if required
        if (isFirstColumnZero) {
            for (int i = 0; i < M; i++) {
                matrix[0][i] = 0;
            }
        }

       for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            System.out.print(matrix[i][j] + " ");
        }

        System.out.println();
       }
    }
}