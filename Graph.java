package CrimeDispatch;

import java.util.Set;

public interface Graph<V, E> {

    Set<V> vertices;
    Set<V> edges;
    // Add/Remove vertices
    void addVertex(V v);
    void removeVertex(V v);

    // Add/remove edges
    void addEdge(E e);
    void removeEdge(E e);

    // Query operations
    boolean containsVertex(V v);
    boolean containsEdge(E e);

    // Traversal operations
    Set<V> neighbors(V v);
}
