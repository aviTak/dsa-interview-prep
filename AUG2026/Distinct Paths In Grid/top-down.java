import java.util.*;

class Main {
    public static void main(String args[]) {
        int m = 10, n = 4;

        int paths = gridPaths(m, n);
        System.out.println(paths);
    }

    public static int gridPaths(int m, int n) {
        int memo[][] = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j] = -1;
            }
        }

        return gridPaths(memo, 0, 0);
    }

    public static int gridPaths(int[][] memo, int m, int n) {
        if (m == memo.length - 1 && n == memo[0].length - 1) {
            return 1;
        }

        if (m >= memo.length || n >= memo[0].length) {
            return 0;
        }

        if (memo[m][n] != -1) {
            return memo[m][n];
        }

        int right = gridPaths(memo, m, n + 1),
            down = gridPaths(memo, m + 1, n);

        memo[m][n] = right + down;
        return memo[m][n];
    }
}