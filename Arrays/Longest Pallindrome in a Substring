public class Program
{
    public static void main(String[] args) {
		String a = "aaaabbaa";
        int N = a.length();

        int pal[][] = new int[N][N];
        pal[0][0] = 0;

        int max = 1, x = -1, y = -1; 

        for(int i = 1; i < N; i++) {
            pal[i][i] = 1; // For length = 1

            if(a.charAt(i) == a.charAt(i - 1)) {// For length = 2
                pal[i - 1][i] = 1;

                if(max < 2) {
                    max = 2;
                    x = i - 1;
                    y = i;
                }
            } else {
                pal[i - 1][i] = 0;
            }
        }


        for(int size = 3; size <= N; size++) {
            for(int start = 0; start <= N - size; start++) {
                int end = start + size - 1;
                if(a.charAt(start) == a.charAt(end) && pal[start + 1][end - 1] == 1) {
                    pal[start][end] = 1;

                    if(max < size) {
                        max = size;
                        x = start;
                        y = end;
                    }
                }
            }
        }

        System.out.println(a.substring(x, y + 1) + " " + max);
	}
}

/*
    Input:
        0   1   2   3   4   5   6   7
    S = "a  a   a   a   b   b   a   a"
                a   a   b   b   a   a

    Explanation: The longest Palindromic
    substring is "aabbaa".

arr[i][i] = 1
arr[i-1][i] --> 1 or 0

// 1
// 2

// 3
0 -> 2
1 -> 3
2 -> 4
3 -> 5
4 -> 6
5 -> 7

8 - 3


// 4
0 -> 3
1 -> 4

// 5
0 -> 4
1 -> 5


        0   1   2   3   4   5   6   7

    0   1   1   1   1   0   0   0   0   
    
    1       1   1   1   0   0   0   0

    2           1   1   0   0   0   1

    3               1   0   0   1   0

    4                   1   1   0   0

    5                       1   0   0

    6                           1   1

    7                               1
*/





