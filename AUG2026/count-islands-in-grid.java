import java.util.*;


class Solution {
    public static void main(String args[]) {
        int grid[][] = {{1,0},{0,0},{0,1},{0,1},{1,1}};

        System.out.println(countGridIslands(grid));
    }

    public static int countGridIslands(int[][] grid) {
        int M = grid.length,
            N = grid[0].length,
            count = 0;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }

                grid[i][j] = 0;
                count++;

                // Move in all four directions to exclude all the land
                dfs(grid, i + 1, j);
                dfs(grid, i, j + 1);
                dfs(grid, i - 1, j);
                dfs(grid, i, j - 1);
            }
        }

        return count;
    }

    public static void dfs(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }

        if (grid[i][j] == 0) {
            return;
        }

        grid[i][j] = 0;

        // Move in all four directions to exclude all the land
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }
}