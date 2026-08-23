import java.util.*;

class Edge {
    int weight;
    Node target;
    
    Edge(Node target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Node {
    int id;
    ArrayList<Edge> edges;
    
    Node(int id) {
        this.id = id;
        edges = new ArrayList<>();
    }
    
    public void addEdge(Node target, int weight) {
        edges.add(new Edge(target, weight));
    }
}

class Solution {
    public static void main(String args[]) {
        // Create tree nodes
        Node root = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node leaf3 = new Node(3); // Leaf
        Node leaf4 = new Node(4); // Leaf
        Node leaf5 = new Node(5); // Leaf

        // Construct tree edges with weights:
        //          root (0)
        //         /        \
        //   (wt: 4)        (wt: 2)
        //       v            v
        //     n1 (1)       n2 (2)
        //     /    \         \
        // (wt:1)  (wt:8)    (wt:3)
        //   v        v        v
        // leaf3    leaf4    leaf5

        root.addEdge(n1, 4);
        root.addEdge(n2, 2);

        n1.addEdge(leaf3, 1);
        n1.addEdge(leaf4, 8);

        n2.addEdge(leaf5, 3);

        int result = minEdgePathSum(root);
        
        System.out.println("Minimum root-to-leaf path weight: " + result);
        // Expected output: 5 (via root -> n1 -> leaf3: 4 + 1 = 5, OR root -> n2 -> leaf5: 2 + 3 = 5)
    }
    
    public static int minEdgePathSum(Node root) {
        if (root == null) {
            return 0;
        }
        
        if (root.edges.isEmpty()) {
            return 0;
        }
        
        int minSum = Integer.MAX_VALUE;
        
        for (Edge edge : root.edges) {
            int path = edge.weight + minEdgePathSum(edge.target);
            
            minSum = Math.min(minSum, path);
        }
        
        return minSum;
    }
}




