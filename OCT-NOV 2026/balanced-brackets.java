import java.util.*;

class Solution {
    public static void main(String args[]) {
        String str = "([]){}";
        System.out.println(isBalanced(str)); 
    }

    public static boolean isBalanced(String str) {
        int N = str.length();

        if (N % 2 != 0) {
            return false;
        }

        Map<Character, Character> map = Map.of(
            ')', '(',
            '}', '{',
            ']', '['
        );

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            char g = str.charAt(i);

            if (map.containsKey(g)) {
                // It is closing one
                if (stack.isEmpty() || stack.pop() != map.get(g)) {
                    return false;
                }
            } else {
                stack.push(g);
            }
        }

        return true;
    }
}
