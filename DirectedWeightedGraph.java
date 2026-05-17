package CrimeDispatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedWeightedGraph<V,E extends CrimeDispatch.Edge> implements CrimeDispatch.Graph<V, E> { // ensuring Edge has neccessary functions despite being generic

    private class Vertex<T> {
        public T value;
        public Vertex(T value) { this.value = value;}
    }

    // Using helper class to differentiate direction of edges
    public class EdgeContainer<E> {
        public Set<E> incoming = new HashSet<>();
        public Set<E> outgoing = new HashSet<>();
    }

    Map<V, EdgeContainer<E>> graph = new HashMap<>();

    public boolean addVertex(V v) {
        if (graph.containsKey(v)) return false;
        graph.put(v, new EdgeContainer<>());
        return true;
    }

    public boolean containsVertex(V v) {
        return graph.containsKey(v);
    }

    public Set<V> vertexSet() {
        return graph.keySet();
    }

    public boolean removeVertex(V v) {
        if (graph.containsKey(v)) {
            graph.remove(v);
            return true;
        }
        return false;
    }

    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        EdgeContainer<E> sourceEdgeContainer = graph.get(sourceVertex);
        EdgeContainer<E> targetEdgeContainer = graph.get(targetVertex);

        // check if either vertex is missing before changing anything
        if (sourceEdgeContainer == null || targetEdgeContainer == null) {
            return false;
        }

        // add vertices
        sourceEdgeContainer.outgoing.add(e);
        targetEdgeContainer.incoming.add(e);
        return true;
    }

    public boolean containsEdge(E e) {
        return false;
    }

    public E getEdge(V sourceVertex, V targetVertex) {
        return null;
    }

    public Set<E> edgeSet() {
        return Set.of();
    }

    public boolean removeEdge(E e) {
        return false;
    }

    public V getEdgeSource(E e) {
        return null;
    }

    public V getEdgeTarget(E e) {
        return null;
    }

    public double getEdgeWeight(E e) {
        return 0;
    }

    public void setEdgeWeight(E e, double weight) {

    }

    public Set<E> outgoingEdgesOf(V vertex) {
        return Set.of();
    }

    public Set<E> incomingEdgesOf(V vertex) {
        return Set.of();
    }
}