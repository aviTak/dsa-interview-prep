import java.util.ArrayList;

class NQueen {
    static ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    public static void NQueen(ArrayList<Integer> res, int N, int row, boolean visitedColumns[], boolean visitedDiagonals1[], boolean visitedDiagonals2[]) {
        // Base case: all rows processed
        if (row >= N) {
            result.add(new ArrayList<>(res)); // Store a deep copy of res
            return;
        }

        for (int column = 0; column < N; column++) {
            // Check if placing a queen is safe
            if (visitedColumns[column] || visitedDiagonals1[row + column] || visitedDiagonals2[row - column + N - 1]) {
                continue;
            }

            // Place the queen
            res.add(column);
            visitedColumns[column] = true;
            visitedDiagonals1[row + column] = true;
            visitedDiagonals2[row - column + N - 1] = true;

            // Recur for the next row
            NQueen(res, N, row + 1, visitedColumns, visitedDiagonals1, visitedDiagonals2);

            // Backtrack: remove the queen and reset the visited arrays
            res.remove(res.size() - 1);
            visitedColumns[column] = false;
            visitedDiagonals1[row + column] = false;
            visitedDiagonals2[row - column + N - 1] = false;
        }
    }

    public static void main(String args[]) {
        int N = 4; // You can change N to test other board sizes
        ArrayList<Integer> res = new ArrayList<>();
        boolean visitedColumns[] = new boolean[N];
        boolean visitedDiagonals1[] = new boolean[2 * N - 1];
        boolean visitedDiagonals2[] = new boolean[2 * N - 1];

        // Start solving the N-Queens problem
        NQueen(res, N, 0, visitedColumns, visitedDiagonals1, visitedDiagonals2);

        // Print all solutions
        for (ArrayList<Integer> solution : result) {
            for (int column : solution) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
    }
}
