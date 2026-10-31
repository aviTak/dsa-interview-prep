import java.util.*;

class Main {
    public static void main(String args[]) {
        int walls[] = {1,4,2,3};
        System.out.println(maxWater(walls));
    }

    public static int maxWater(int[] walls) {
        int N = walls.length, i = 0, j = N - 1, area = 0;

        if (N <= 1) {
            return 0;
        }

        while (i < j) {
            area = Math.max(area, (j - i) * Math.min(walls[i], walls[j]));

            if (walls[i] >= walls[j]) {
                j--;
            } else {
                i++;
            }
        }

        return area;
    }
}