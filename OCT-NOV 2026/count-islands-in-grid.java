import java.util.*;

class Solution {
    public static void main(String args[]) {
        int[][] grid = {{1,0},{0,0},{0,1},{0,1},{1,1}};

        System.out.println(countIslands(grid));
    }

    public static int countIslands(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int count = 0, M = grid.length, N = grid[0].length;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] != 0) {
                    count++;
                    // Explore
                    dfs(grid, i, j);
                }
            }
        }

        return count;
    }

    private static void dfs(int[][] grid, int i, int j) {
        int M = grid.length, N = grid[0].length;

        if (i < 0 || i >= M || j < 0 || j >= N || grid[i][j] == 0) {
            return;
        }

        grid[i][j] = 0;

        // Move in all 4 directions
        dfs(grid, i  + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }
}
