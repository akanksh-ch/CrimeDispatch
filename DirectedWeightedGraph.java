package CrimeDispatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedWeightedGraph<V, E> implements CrimeDispatch.Graph<V, E> {

    private class Vertex<T> {
        public T value;
    }

    private class Edge<V, W> {
        public V Va;
        public V Vb;

        public W weight; // Weight can be generic, e.g. double, int, etc.
    }

    // Using helper class to differentiate direction of edges
    public class EdgeContainer<E> {
        public Set<E> incoming = new HashSet<>();
        public Set<E> outgoing = new HashSet<>();
    }

    Map<V, EdgeContainer<E>> graph = new HashMap<>();

    public boolean addVertex(V v) {

    }

    public boolean containsVertex(V v) {
        return false;
    }

    public Set<V> vertexSet() {
        return Set.of();
    }

    public boolean removeVertex(V v) {
        return false;
    }

    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        return false;
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