package CrimeDispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dijkstra<V extends CrimeDispatch.Vertex, E extends CrimeDispatch.Edge<V, W>, W extends Comparable<W>> {
    private final CrimeDispatch.DirectedWeightedGraph<V, E, W> graph;

    public Dijkstra(CrimeDispatch.DirectedWeightedGraph<V, E, W> graph) {
        this.graph = graph;
    }

    public List<CrimeDispatch.Vertex> findShortestRoute(V from, V to) {
        Map<CrimeDispatch.Vertex, Double> dist = new HashMap<>();

        // Initialise all vertices

        for (V vertex : graph.vertexSet()) {
            dist.put(vertex, Double.MAX_VALUE);
        }

        // set distance from source to be 0
        dist.replace(from, 0.0);

        return new ArrayList<>();
    }
}
