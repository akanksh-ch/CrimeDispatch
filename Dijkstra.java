package CrimeDispatch;

import java.util.*;

public class Dijkstra<V extends CrimeDispatch.Vertex, E extends CrimeDispatch.Edge<V, W>, W extends Comparable<W>> {
    private final CrimeDispatch.DirectedWeightedGraph<V, E, W> graph;

    public Dijkstra(CrimeDispatch.DirectedWeightedGraph<V, E, W> graph) {
        this.graph = graph;
    }

    public List<CrimeDispatch.Vertex> findShortestRoute(V from, V to) {
        Map<CrimeDispatch.Vertex, Double> dist = new HashMap<>(); // distance between source and 'u' vertex
        Map<CrimeDispatch.Vertex, Double> prev = new HashMap<>(); // previously found solutions
        Set<V> toExplore = new HashSet<>(); // unexplored vertices

        // Initialise all vertices

        for (V vertex : graph.vertexSet()) {
            dist.put(vertex, Double.MAX_VALUE);
            toExplore.add(vertex);
        }

        // set distance from source to be 0
        dist.replace(from, 0.0);

        while (!toExplore.isEmpty()) {
           // grab vertex with smallest distance from source?

            // remove it


            // go through all neighbors and update prev and dist
        }
        return new ArrayList<>();
    }
}
