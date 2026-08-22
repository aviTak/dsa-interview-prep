import java.util.*;

class UnrolledLinkList {
    class Node {
        Node next;
        ArrayList<Integer> values;

        Node() {
            next = null;
            values = new ArrayList<>();
        }
    }

    private final int capacity;
    private Node head;

    public UnrolledLinkList(int capacity) {
        head = null;
        this.capacity = capacity;
    }

    public void add(int value) {
        if (capacity <= 0) {
            return;
        }

        if (head == null) {
            head = new Node();
            head.values.add(value);

            return;
        }

        Node curr = head;

        while (curr.next != null) {
            curr = curr.next;
        }

        if (curr.values.size() < capacity) {
            curr.values.add(value);
        } else {
            Node node = new Node();
            node.values.add(value);

            curr.next = node;
        }
    }

    public boolean delete(int value) {
        Node curr = head;

        while(curr != null) {
            if (curr.values.contains(value)) {
                curr.values.remove(Integer.valueOf(value));

                if (curr.values.isEmpty()) {
                    removeNode(curr);
                }

                return true;
            }
            curr = curr.next;
        }

        return false;
    }

    public void display() {
        Node curr = head;

        while (curr != null) {
            System.out.println(curr.values);
            curr = curr.next;
        }
    }

    private void removeNode(Node node) {
        if (node == head) {
            head = head.next;
            return;
        }

        Node curr = head;

        while (curr.next != node) {
            curr = curr.next;
        }

        curr.next = node.next;
    }
}