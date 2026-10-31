import java.util.*;

class Main {
    public static void main(String args[]) {
        Stream st = new Stream();
        st.add(1);
        st.add(2);
        st.add(3);

        System.out.println(st.median());
    }
}

class Stream {
    private final PriorityQueue<Integer> small;
    private final PriorityQueue<Integer> big;

    public Stream() {
        small = new PriorityQueue<>(Collections.reverseOrder()); // M ax Heap
        big = new PriorityQueue<>(); // Min Heap
    }

    public void add(int num) {
        small.offer(num);
        big.offer(small.poll());

        if (small.size() < big.size()) {
            small.offer(big.poll());
        }
    }

    public double median() {
        if (small.size() > big.size()) {
            return small.poll();
        };

        return (small.poll() + big.poll()) / 2.0;
    }
}
