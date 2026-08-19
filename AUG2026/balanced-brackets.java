import java.util.*;

class BalancedBrackets {
    public static void main(String args[]) {
        String str = "([]){}";

        System.out.println(isBalancedBrackets(str));
    }

    public static boolean isBalancedBrackets(String str) {
        Stack<Character> st = new Stack<>();
        int N = str.length();

        for (int i = 0; i < N; i++) {
            char g = str.charAt(i);

            if (g == '(' || g == '{' || g == '[') {
                st.push(g);
            } else if (g == ')' || g == '}' || g == ']') {
                if (st.isEmpty()) {
                    return false;
                }
                char top = st.peek();

                if (g == ')' && top != '(' || g == '}' && top != '{' || g == ']' && top != '[') {
                    return false;
                }
                
                st.pop();
            } else {
                return false;
            }
        }

        if (st.isEmpty()) {
            return true;
        }

        return false;
    }
}