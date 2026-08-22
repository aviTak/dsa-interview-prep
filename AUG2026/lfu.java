import java.util.*;

class LFU {
    class Node {
        int key;
        int value;
        int freq;

        Node next;
        Node prev;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
            this.freq = 1;
        }
    }
    
    class LL {
        int size;
        Node head;
        Node tail;

        LL() {
            size = 0;

            head = new Node(-1, -1);
            tail = new Node(-1, -1);

            head.next = tail;
            tail.prev = head;
        }

        public void add(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;

            size++;
        }

        public void delete(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;

            size--;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    private final int capacity;
    private int minFreq;

    private final HashMap<Integer, Node> cacheMap;
    private final HashMap<Integer, LL> freqMap; // freq, LL

    public LFU(int capacity) {
        this.capacity = capacity;
        minFreq = 0;

        cacheMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if (!cacheMap.containsKey(key)) {
            return -1;
        }

        Node node = cacheMap.get(key);
        increaseFreq(node);

        return node.value;
    }

    private void increaseFreq(Node node) {
        LL list = freqMap.get(node.freq);
        list.delete(node);

        if (node.freq == minFreq && list.isEmpty()) {
            freqMap.remove(node.freq);
            minFreq++;
        }

        node.freq++;
        
        freqMap.computeIfAbsent(node.freq, k -> new LL()).add(node);
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        if (cacheMap.containsKey(key)) {
            Node node = cacheMap.get(key);
            node.value = value;
            increaseFreq(node);

            return;
        }

        if (cacheMap.size() == capacity) {
            // Remove last node in minFreq
            LL list = freqMap.get(minFreq);
            cacheMap.remove(list.tail.prev.key);

            list.delete(list.tail.prev);
            
            if (list.isEmpty()) {
                
                freqMap.remove(minFreq);
            }
        }

        Node node = new Node(key, value);

        cacheMap.put(key, node);
        freqMap.computeIfAbsent(1, k -> new LL()).add(node);
        
        minFreq = 1;
    }
}