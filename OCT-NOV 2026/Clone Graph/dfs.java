import java.util.*;

class Main {
    public static void main(String args[]) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);

        Graph gh = new Graph();
        Node clonedGraphRootNode = gh.clone(node1);

        System.out.println("Original Graph:");
        gh.print(node1);

        System.out.println("\nCloned Graph:");
        gh.print(clonedGraphRootNode);
    }
}

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int val){
        this.val = val;
        neighbors = new ArrayList<Node>();
    }
}

class Graph {
    public void print(Node node) {
        print(node, new HashMap<>());
    }

    private static void print(Node node, Map<Node, Boolean> visited) {
        if (node == null || visited.containsKey(node)) {
            return;
        }
        visited.put(node, true);

        System.out.print("Node " + node.val + " with neighbors: ");

        for (Node n : node.neighbors) {
            System.out.print(n.val + " ");
        }

        System.out.println();

        for (Node n : node.neighbors) {
            print(n, visited);
        }
    }

    public static Node clone(Node node) {
        return clone(node, new HashMap<Node, Node>());
    }

    private static Node clone(Node og, Map<Node, Node> visited) {
        if (visited.containsKey(og)) {
            return visited.get(og);
        }

        Node cloned = new Node(og.val);
        visited.put(og, cloned);

        for (Node neighbor : og.neighbors) {
            cloned.neighbors.add(clone(neighbor, visited));
        }

        return cloned;
    }
}
