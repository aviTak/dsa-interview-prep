import java.util.*;

class LinkedList {
    class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            next = null;
        }
    }

    private Node head;

    public LinkedList() {
        head = null;
    }

    public void add(int value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
    }

    public void display() {
        Node curr = head;

        while (curr != null) {
            System.out.println(curr.value);
            curr = curr.next;
        }
    }

    public void reverse() {
        Node curr = head,
            prev = null;

        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            
            prev = curr;
            curr = next;
        }

        head = prev;
    }
}

class Solution {
    public static void main(String args[]) {
        LinkedList list = new LinkedList();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.display();
        list.reverse();
        list.display();
    }
}

