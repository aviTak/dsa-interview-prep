import java.util.*;

class Node {
    int value;
    Node next;
    
    Node(int value) {
        this.value = value;
    }
}

class Solution {
    public static void main(String args[]) {
        Node head = new Node(3);
        head.next = new Node(2);
        head.next.next = new Node(0);
        head.next.next.next = new Node(-4);

        // Create cycle: -4 -> 2
        head.next.next.next.next = head.next;
        
        System.out.println(hasCycle(head));
    }
    
    public static int hasCycle(Node head) {
        if (head == null || head.next == null) {
            return -1;
        }
        
        Node slow = head,
            fast = head;
            
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                Node ptr = head;
                
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                
                return ptr.value;
            }
        }
        
        return -1;
    }
}
