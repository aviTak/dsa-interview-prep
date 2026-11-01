import java.util.*;

class Solution {
    public static void main(String args[]) {
        char grid[][] = {{'a','b','c','e'},{'s','f','c','t'},{'a','d','e','e'}};
        String target = "tee";

        System.out.println(isFound(grid, target));
    }

    public static boolean isFound(char[][] grid, String target) {
        // Start from wevery single char the backtrack
        int M = grid.length;

        if (grid.length == 0) {
            return target.isEmpty();
        }

        int N = grid[0].length;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == target.charAt(0) && dfs(grid, target, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean dfs(char[][] grid, String target, int i, int j, int index) {
        if (index == target.length()) {
            return true;
        }

        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != target.charAt(index)) {
            return false;
        }

        char og = grid[i][j];
        grid[i][j] = '#';

        // Move in all four directions
        boolean isFound = dfs(grid, target, i + 1, j, index + 1) ||
            dfs(grid, target, i, j + 1, index + 1) ||
            dfs(grid, target, i - 1, j, index + 1) ||
            dfs(grid, target, i, j - 1, index + 1);

        // Backtrack
        grid[i][j] = og;
        
        return isFound;
    }
}