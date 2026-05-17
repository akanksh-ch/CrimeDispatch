package CrimeDispatch;

import java.util.Set;

public class DirectedWeightedGraph<V, E> implements Graph<V, E> {
    public boolean addVertex(V v) {
        return false;
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