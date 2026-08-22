import java.util.*;

class LinkedList {
    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            next = null;
        }
    }

    Node head;

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
        LinkedList list1 = new LinkedList(),
            list2 = new LinkedList();

        
        // listA = [-3,-1,9,10], listB = [-10,3,4,6,9]

        list1.add(10);
        list1.add(9);
        list1.add(-1);
        list1.add(-3);

        list1.display();

        list2.add(9);
        list2.add(6);
        list2.add(-4);
        list2.add(3);
        list2.add(-10);

        list2.display();

        LinkedList.Node merged = linkedListCombineTwoSorted(list1.head, list2.head);
        display(merged);
    }

    public static void display(LinkedList.Node head) {
        LinkedList.Node curr = head;

        while (curr != null) {
            System.out.println(curr.value);
            curr = curr.next;
        }
    }

    public static LinkedList.Node linkedListCombineTwoSorted(LinkedList.Node l1, LinkedList.Node l2) {
        LinkedList.Node dummy = new LinkedList.Node(0);
        LinkedList.Node tail = dummy;

        while (l1 != null && l2 != null) {
            if (l1.value <= l2.value) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }

            tail = tail.next;
        }

        tail.next = l1 == null? l2 : l1;

        return dummy.next;
    }


}

