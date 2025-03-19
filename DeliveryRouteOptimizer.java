import java.util.*;
//Weighted Graphs, Dijkstra’s Algorithm, Weighted Edges  

//Simulate a delivery system where cities are **nodes**, and roads have weights (distances)
//Dijkstra’s Algorithm to find the shortest delivery route between two cities.  


/*outline
map as a graph (cities = nodes, roads = edges)
You give each road a weight (ex: road from A → B = 5 miles)
You use Dijkstra’s Algorithm where:
Always picks the shortest known path first.
Updates neighbors with new shortest distances.
Keeps doing  until finds the best route to your destination.

Graph stored in a HashMap → Cities (keys) connect to roads (values).
Priority Queue (Min Heap) → Always process the nearest city first.

     [A] ----(4)---- [B] ----(2)---- [C]
      |             /    \
     (1)         (3)    (7)
      |          /        \
     [D] ----(5)---- [E] ----(1)---- [F]


*/

/*case 2
A delivery driver starts from Warehouse (W) and needs to deliver to Customer (C).
The roads have different travel times (weights).
Dijkstra’s Algorithm finds the fastest route.

  (W) --10--> (A) --20--> (C)
   |            |
  30           5
   |            |
  (B) --------> (D)
       15

fastest route:
W → A → D → C (25 min total)
 * 
 */


public class DeliveryRouteOptimizer {
    public Map<String, List<Edge>> graph = new HashMap<>();

    //Dijkstra's Algorithm Implementation
    public Map<String, Integer> findShortestPath(String start) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance)); //Priority Queue (Min Heap) Always processes the nearest city first to guarantee we find the shortest path efficiently.

        pq.add(new Edge(start, 0));

        Map<String, Integer> shortestDistances = new HashMap<>();
        shortestDistances.put(start, 0);

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            String city = current.city;
            int distance = current.distance;

            for (Edge neighbor : graph.getOrDefault(city, new ArrayList<>())) {
                int newDistance = distance + neighbor.distance;

                if (newDistance < shortestDistances.getOrDefault(neighbor.city, Integer.MAX_VALUE)) {
                    shortestDistances.put(neighbor.city, newDistance);
                    pq.add(new Edge(neighbor.city, newDistance));
                }
            }
        }

        return shortestDistances;
    }

    // Add roads to the graph
    public void addRoad(String city1, String city2, int distance) {
        graph.putIfAbsent(city1, new ArrayList<>());
        graph.putIfAbsent(city2, new ArrayList<>());
        graph.get(city1).add(new Edge(city2, distance));
        graph.get(city2).add(new Edge(city1, distance));
    }

    static class Edge {
        String city;
        int distance;
        
        Edge(String city, int distance) {
            this.city = city;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        DeliveryRouteOptimizer dijkstra = new DeliveryRouteOptimizer();
        dijkstra.addRoad("A", "B", 4);
        dijkstra.addRoad("B", "C", 2);
        dijkstra.addRoad("A", "C", 5);

        Map<String, Integer> shortestPaths = dijkstra.findShortestPath("A");

        System.out.println(shortestPaths);  //{A=0, B=4, C=6}
    }
}
