import java.util.*; 


//  Nearest Gas Station Finder (Greedy Algorithm & Graphs)**  
//Greedy Algorithm, Weighted Graphs, Traveling Salesman  

// - A **car starts with limited fuel** and needs to reach a destination.  
// - Find the **nearest gas station at each step** (greedy strategy).  
// - Compare with **Dijkstra’s shortest path** to show the difference.  


// - Implement **Greedy Algorithm (choosing nearest gas station)**.  
// - Compare it with **Dijkstra’s shortest path approach**.  


 public class nearestGasStation {
     // Define the graph as an adjacency list (HashMap of HashMaps)
     private static final Map<String, Map<String, Integer>> graph = new HashMap<>();
 
     // Initialize the graph
     static {
         graph.put("Start", Map.of("A", 5, "B", 10));
         graph.put("A", Map.of("C", 10, "Gas1", 3));
         graph.put("B", Map.of("C", 5, "Gas2", 4));
         graph.put("C", Map.of("Destination", 8));
         graph.put("Gas1", Map.of("C", 2));
         graph.put("Gas2", Map.of("C", 3));
         graph.put("Destination", new HashMap<>());
     }
 
     // Greedy Algorithm: Always choose the nearest gas station
     public static List<String> greedyGasStation(String start, String destination, int fuel) {
         List<String> path = new ArrayList<>();
         String current = start;
         path.add(current);
 
         while (!current.equals(destination)) {
             Map<String, Integer> neighbors = graph.getOrDefault(current, new HashMap<>());
             if (neighbors.isEmpty()) return List.of("No Path Found");
 
             // Find the nearest neighbor
             String nearest = Collections.min(neighbors.entrySet(), Map.Entry.comparingByValue()).getKey();
             int distance = neighbors.get(nearest);
 
             // If fuel is not enough, refuel (assume 15 units refueling)
             if (fuel < distance) {
                 fuel = 15;
             }
             fuel -= distance;
             if (fuel < 0) return List.of("Ran out of fuel");
 
             path.add(nearest);
             current = nearest;
         }
         return path;
     }
 
     //Dijkstra's Algorithm: Find the shortest path
     public static List<String> dijkstra(String start, String destination) {
         PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
         pq.add(new Node(start, 0, new ArrayList<>()));
 
         Set<String> visited = new HashSet<>();
 
         while (!pq.isEmpty()) {
             Node currentNode = pq.poll();
             String current = currentNode.name;
             int cost = currentNode.cost;
             List<String> path = new ArrayList<>(currentNode.path);
             path.add(current);
 
             if (visited.contains(current)) continue;
             visited.add(current);
 
             if (current.equals(destination)) return path;
 
             for (Map.Entry<String, Integer> neighbor : graph.getOrDefault(current, new HashMap<>()).entrySet()) {
                 if (!visited.contains(neighbor.getKey())) {
                     pq.add(new Node(neighbor.getKey(), cost + neighbor.getValue(), path));
                 }
             }
         }
         return List.of("No Path Found");
     }
 
     // Helper class for priority queue in Dijkstra’s Algorithm
     static class Node {
         String name;
         int cost;
         List<String> path;
 
         Node(String name, int cost, List<String> path) {
             this.name = name;
             this.cost = cost;
             this.path = path;
         }
     }
 
     //runner
     public static void main(String[] args) {
         System.out.println("Greedy Path: " + greedyGasStation("Start", "Destination", 10));
         System.out.println("Dijkstra's Path: " + dijkstra("Start", "Destination"));
     }
 }
 