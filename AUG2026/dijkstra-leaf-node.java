import java.util.*;

class Vertex {
    int id;
    ArrayList<Edge> edges;
    
    Vertex(int id) {
        this.id = id;
        edges = new ArrayList<>();
    }
    
    void addEdge(Vertex target, int weight) {
        this.edges.add(new Edge(target, weight));
    }
}

class Edge {
    int weight;
    Vertex target;
    
    Edge(Vertex target, int weight) {
        this.weight = weight;
        this.target = target;
    }
}

class Pair {
    Vertex node;
    int dist;
    
    Pair (Vertex node, int dist) {
        this.node = node;
        this.dist = dist;
    }
}

class Solution {
    public static void main(String args[]) {
        Vertex v0 = new Vertex(0);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4); // Leaf
        Vertex v5 = new Vertex(5); // Leaf

        // Build Directed Graph:
        // 0 -> (1, wt: 10)
        // 0 -> (2, wt: 2)
        // 1 -> (4, wt: 5)   [Path: 0 -> 1 -> 4 = 15]
        // 2 -> (3, wt: 3)
        // 3 -> (5, wt: 1)   [Path: 0 -> 2 -> 3 -> 5 = 6]
        // 3 -> (1, wt: 1)   [Shortcut: 0 -> 2 -> 3 -> 1 -> 4 = 11]

        v0.addEdge(v1, 10);
        v0.addEdge(v2, 2);

        v1.addEdge(v4, 5);

        v2.addEdge(v3, 3);

        v3.addEdge(v5, 1);
        v3.addEdge(v1, 1);
        
        int minSum = findMinLeafPathDijkstra(v0);
    
        System.out.println("Minimum path sum to a leaf: " + minSum);
    }
    
    public static int findMinLeafPathDijkstra(Vertex start) {
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
        
        HashMap<Integer, Integer> res = new HashMap<>();
        
        pq.offer(new Pair(start, 0));
        res.put(start.id, 0);
        
        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            Vertex u = curr.node;
            int d = curr.dist;
            
            if (d > res.getOrDefault(u.id, Integer.MAX_VALUE)) {
                continue;
            }
            
            if (u.edges.isEmpty()) {
                return d;
            }
            
            for (Edge edge : u.edges) {
                Vertex next = edge.target;
                int newDist = d + edge.weight;
                
                if (newDist < res.getOrDefault(next.id, Integer.MAX_VALUE)) {
                    res.put(next.id, newDist);
                    pq.offer(new Pair(next, newDist));
                }
            }
        }
        
        return -1;
    }
    
}




