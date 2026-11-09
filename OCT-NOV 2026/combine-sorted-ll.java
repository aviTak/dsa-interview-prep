import java.util.*;

class Solution {
    public static void main(String args[]) {
        LinkedList l1 = new LinkedList();
        l1.insert(40);
        l1.insert(5);
        l1.insert(3);
        l1.insert(1);

        LinkedList l2 = new LinkedList();
        l2.insert(10);
        l2.insert(4);
        l2.insert(2);
        l2.insert(-1);

        l1.combine(l2);
        l1.display();
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
            System.out.print(t.val + " ");
            t = t.next;
        }
    }

    public void combine(LinkedList l2) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node tail = dummy;

        Node i = head,
            j = l2.head;

        while (i != null && j != null) {
            if (i.val <= j.val) {
                tail.next = i;
                i = i.next;
            } else {
                tail.next = j;
                j = j.next;
            }
            tail = tail.next;
        }

        tail.next = i != null? i : j;
        head = dummy.next;
    }
}
