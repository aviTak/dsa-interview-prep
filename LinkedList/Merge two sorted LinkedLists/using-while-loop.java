class Node {
    int data;
    Node next;

    public Node() {
        next = null;
    }

    public Node(int data) {
        this.data = data;
        next = null;
    }
};

class LinkedList {
    private Node head;

    public LinkedList() {
        head = null;
    }

    public void addFirst(int data) {
        Node t = new Node(data);
        t.next = head;
        head = t;
    }

    public Node getHead() {
        return head;
    }

    public void print() {
        Node t = head;
        while(t != null) {
            System.out.print(t.data + " ");
            t = t.next;
        }
        System.out.println();
    }

    public static void print(Node start) {
        Node t = start;
        while(t != null) {
            System.out.print(t.data + " ");
            t = t.next;
        }
        System.out.println();
    }
}

public class Program {
    public static void main(String[] args) {
	LinkedList l1 = new LinkedList();
        l1.addFirst(9);
        l1.addFirst(7);
        l1.addFirst(5);

        System.out.println("First LinkedList:-");
        l1.print();

        LinkedList l2 = new LinkedList();
        l2.addFirst(8);
        l2.addFirst(6);
        l2.addFirst(4);

        System.out.println("Second LinkedList:-");
        l2.print();

        System.out.println("Sorted LinkedList:-");
        LinkedList.print(merge(l1.getHead(), l2.getHead()));
	}

    public static Node merge(Node head1, Node head2) {
        Node head = null;

        if(head1.data < head2.data) {
            head = head1;
            head1 = head1.next;
        } else {
            head = head2;
            head2 = head2.next;
        }

        Node t = head;

        while(head1 != null && head2 != null) {
            if(head1.data < head2.data) {
                t.next = head1;
                t = t.next;
                head1 = head1.next;
            } else {
                t.next = head2;
                t = t.next;
                head2 = head2.next;
            }
        }

        if(head1 != null)
            t.next = head1;

        if(head2 != null)
            t.next = head2;

        return head;
    }
}
