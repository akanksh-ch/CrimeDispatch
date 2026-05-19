package CrimeDispatch;

import java.nio.file.Path;
import java.util.*;

public class Dijkstra<V extends CrimeDispatch.Vertex, E extends CrimeDispatch.Edge<V, W>, W extends Comparable<W>> {
    private final CrimeDispatch.DirectedWeightedGraph<V, E, W> graph;

    public Dijkstra(CrimeDispatch.DirectedWeightedGraph<V, E, W> graph) {
        this.graph = graph;
    }

    private class PathNode implements Comparable<PathNode> {
        final V vertex;
        final double distance;

        PathNode(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(PathNode other) {
            // To allow sorting of vertices within the priority queue in ascending order
            return Double.compare(this.distance, other.distance);
        }
    }

    public List<CrimeDispatch.Vertex> findShortestRoute(V from, V to) {
        Map<CrimeDispatch.Vertex, Double> dist = new HashMap<>(); // distance between source and 'u' vertex
        Map<CrimeDispatch.Vertex, Double> prev = new HashMap<>(); // previously found solutions
        // Using priority queue to sort through which is the shortest path node/vertex
        PriorityQueue<PathNode> toExplore = new PriorityQueue<>(); // unexplored vertices

        // Initialise all vertices

        for (V vertex : graph.vertexSet()) {
            dist.put(vertex, Double.MAX_VALUE);
        }

        // set distance from source to be 0
        dist.replace(from, 0.0);

        // Add source node as starting node
        toExplore.add(new PathNode(from, 0.0));

        while (!toExplore.isEmpty()) {
           // grab vertex with smallest distance from source
            PathNode first = toExplore.poll();
            V u = first.vertex;
            double d = first.distance;

            // Don't explore more nodes as we already reached destination
            if(u.equals(to)) {
                break;
            }

            // go through all neighbors and update prev and dist
            for (E edge : graph.outgoingEdgesOf(u)) {
                V v = (V) edge.getTarget();

                double alt  = dist.get(u) + graph.getEdgeWeight(edge);

                // Update dist if we find a better path
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);

                    // Add newer shorter path to queue
                    toExplore.offer(new PathNode(v, alt));
                }
            }
        }
        return new ArrayList<>();
    }
}
