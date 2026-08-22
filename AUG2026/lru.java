import java.util.*;

public class lru {
    public static void main(String[] args) {
        LRU a = new LRU(5);
        a.put(1, 1);
        a.put(2, 2);
        a.put(3, 3);
        a.put(4, 4);
        a.put(5, 5);
        
        System.out.println(a.get(1));
        
        a.put(6, 6);
        
        System.out.println(a.get(2));
    }
}

class LRU {
    class Node {
        int key;
        int value;
        Node next;
        Node prev;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
    
    private final int capacity;
    private final HashMap<Integer, Node> map;
    private final Node head;
    private final Node tail;
    
    public LRU(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        
        Node node = map.get(key);
        
        remove(node);
        insert(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        
        if (map.size() == capacity) {
            map.remove((tail.prev.key));
            remove(tail.prev);
        }
        
        Node node = new Node(key, value);
        map.put(key, node);
        insert(node);
    }
    
    private void insert(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
}

