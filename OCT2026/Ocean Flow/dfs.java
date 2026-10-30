import java.util.*;

class Main {
    public static void main(String args[]) {
        int[][] grid = {
            {1, 2, 3, 1},
            {4, 3, 3, 4},
            {5, 2, 4, 3},
            {5, 2, 1, 5},
            {1, 5, 1, 3}
        };

        System.out.println(oceanFlow(grid));
    }

    public static List<List<Integer>> oceanFlow(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new ArrayList<>();
        }

        int M = grid.length, N = grid[0].length;
        boolean topLeft[][] = new boolean[M][N],
            bottomRight[][] = new boolean[M][N];

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            dfs(grid, topLeft, i, 0);
            dfs(grid, bottomRight, i, N - 1);
        }

        for (int i = 0; i < N; i++) {
            dfs(grid, topLeft, 0, i);
            dfs(grid, bottomRight, M - 1, i);
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (topLeft[i][j] && bottomRight[i][j]) {
                    res.add(new ArrayList<Integer>(Arrays.asList(i, j)));
                }
            }
        }

        return res;
    }

    private static void dfs(int[][] grid, boolean[][] visited, int r, int c) {
        visited[r][c] = true;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i],
                nc = c + dc[i];

            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && !visited[nr][nc] && grid[nr][nc] >= grid[r][c]) {
                dfs(grid, visited, nr, nc);
            }
        }
    }
}
