import java.util.*;

class Solution {
    public static void main(String args[]) {
        LinkedList ll = new LinkedList();
        ll.insert(5);
        ll.insert(4);
        ll.insert(3);
        ll.insert(2);
        ll.insert(1);
        ll.display();
        ll.delete(2);
        ll.display();
    }    
}

class LinkedList {
    private static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    private Node head;

    public void insert(int val) {
        Node node = new Node(val);

        node.next = head;
        head = node;
    }

    public void display() {
        Node t = head;

        while (t != null) {
            System.out.println(t.val);
            t = t.next;
        }
    }

    public void delete(int n) {
        if (head == null || n <= 0) {
            return;
        }

        Node p1 = head,
            p2 = head;

        for (int i = 0; i < n; i++) {
            p2 = p2.next;
        }

        if (p2 == null) {
            // Delete head
            head = head.next;
            return;
        }

        while (p2.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        p1.next = p1.next.next;
    }
}
