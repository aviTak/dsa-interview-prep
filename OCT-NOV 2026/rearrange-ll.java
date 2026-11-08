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
        ll.rearrange();
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

    public void rearrange() {
        if (head == null || head.next == null) {
            return;
        }

        // Find mid-point
        Node slow = head, fast = head;

        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow is the mid-point
        Node ptr = slow.next;
        slow.next = null;

        // Reverse the second half
        Node prev = null;

        while (ptr != null) {
            Node t = ptr.next;
            ptr.next = prev;
            prev = ptr;
            ptr = t;
        }

        // Alternate
        Node n1 = head, n2 = prev;

        while (n2 != null) {
            Node nn1 = n1.next,
                nn2 = n2.next;

            n1.next = n2;
            n2.next = nn1;

            n1 = nn1;
            n2 = nn2;
        }
    }
}
