import java.util.concurrent.TimeUnit;

class LoopInLinkedList {
    public static void main(String args[]) {
        LinkedList list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        list.createLoop(3);
        list.breakLoop();
        list.display();
    }
}


class LinkedList {
    class Node {
        int data;
        Node next;
        
        public Node() {
            this.data = 0;
            this.next = null;
        }
        
        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    Node head;
    
    public void add(int data) {
        Node nw = new Node(data);
        Node temp = head;
        
        head = nw;
        nw.next = temp;
    }
    
    public void display() {
        Node temp = head;
        
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(Exception e) {}
        }
        
        System.out.println();
    }
    
    public void createLoop(int position) {
        Node last = head;
        
        // Find last node
        while (last.next != null) {
            last = last.next;
        }
        
        Node temp = head;
        
        for (int i = 0; i < position - 1; i++) {
            temp = temp.next;
        }
        
        last.next = temp;
    }
    
    public void breakLoop() {
        Node slow = head, fast = head, prev;
        Boolean isLoop = false;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                System.out.println(slow.data);
                isLoop = true;
                break;
            }
        }
        
        if (!isLoop) {
            return;
        }
        
        slow = head;
        prev = fast;
    
        while (slow != fast) {
            prev = fast;
            slow = slow.next;
            fast = fast.next;
        }
        
        prev.next = null;
    }
}
