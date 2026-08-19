import java.util.*;

public class TopKFrequent {

    // Represents an element currently being considered
    static class Node {
        int value;
        int listIndex;
        int elementIndex;

        Node(int value, int listIndex, int elementIndex) {
            this.value = value;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }
    }

    // Represents a value and its frequency
    static class Frequency {
        int value;
        int count;

        Frequency(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }

    public static List<Integer> topKFrequent(
            List<List<Integer>> lists, int k) {

        // Min-heap ordered by value
        PriorityQueue<Node> mergeHeap =
            new PriorityQueue<>(
                (a, b) -> Integer.compare(a.value, b.value)
            );

        // Initially put the first element of every list
        for (int i = 0; i < lists.size(); i++) {
            if (!lists.get(i).isEmpty()) {
                mergeHeap.offer(
                    new Node(lists.get(i).get(0), i, 0)
                );
            }
        }

        // Min-heap ordered by frequency.
        // It contains at most k elements.
        PriorityQueue<Frequency> topK =
            new PriorityQueue<>(
                (a, b) -> Integer.compare(a.count, b.count)
            );

        while (!mergeHeap.isEmpty()) {

            // Smallest value currently present
            int currentValue = mergeHeap.peek().value;

            int frequency = 0;

            /*
             * Every list whose current element is currentValue
             * contributes exactly once to its frequency.
             */
            while (!mergeHeap.isEmpty()
                    && mergeHeap.peek().value == currentValue) {

                Node node = mergeHeap.poll();

                frequency++;

                // Move to the next element in this list
                int nextIndex = node.elementIndex + 1;

                if (nextIndex < lists.get(node.listIndex).size()) {

                    int nextValue =
                        lists.get(node.listIndex).get(nextIndex);

                    mergeHeap.offer(
                        new Node(
                            nextValue,
                            node.listIndex,
                            nextIndex
                        )
                    );
                }
            }

            // Add current value to top-k heap
            topK.offer(
                new Frequency(currentValue, frequency)
            );

            // Keep only k elements
            if (topK.size() > k) {
                topK.poll();
            }
        }

        /*
         * topK is a min-heap, so extract everything
         * and reverse it to get decreasing frequency.
         */
        List<Frequency> frequencies = new ArrayList<>();

        while (!topK.isEmpty()) {
            frequencies.add(topK.poll());
        }

        frequencies.sort(
            (a, b) -> Integer.compare(b.count, a.count)
        );

        List<Integer> result = new ArrayList<>();

        for (Frequency f : frequencies) {
            result.add(f.value);
        }

        return result;
    }

    public static void main(String[] args) {

        List<List<Integer>> lists = Arrays.asList(
            Arrays.asList(1, 2, 4, 8),
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 3, 5, 11, 12, 16),
            Arrays.asList(1, 2)
        );

        int k = 2;

        System.out.println(topKFrequent(lists, k));
    }
}
