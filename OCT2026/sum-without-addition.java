import java.util.*;

class Main {
    public static void main(String args[]) {
        int a = 5, b = 3;

        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }

        System.out.println(a);
    }
}