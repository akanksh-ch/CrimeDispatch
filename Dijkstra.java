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
        Collection<V> toExplore = new PriorityQueue<>(); // unexplored vertices

        // Initialise all vertices

        for (V vertex : graph.vertexSet()) {
            dist.put(vertex, Double.MAX_VALUE);
            toExplore.add(vertex);
        }

        // set distance from source to be 0
        dist.replace(from, 0.0);

        while (!toExplore.isEmpty()) {
           // grab vertex with smallest distance from source

            // remove it


            // go through all neighbors and update prev and dist
        }
        return new ArrayList<>();
    }
}
