import java.util.*;

class Solution {
    public static void main(String args[]) {
        LinkedList ll = new LinkedList();
        ll.insert(5);
        ll.insert(4);
        ll.insert(3);
        ll.insert(2);
        ll.insert(1);
        ll.randomCycle();
        System.out.println(ll.isCycle());
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

    public void randomCycle() {
        try {
            Node node = head; // 1
            node = node.next.next.next; // 4
            node.next = head.next; // 4 --> 2
        } catch(Exception e) {
            return;
        }
    }

    public boolean isCycle() {
        Node slow = head,
            fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
 }
