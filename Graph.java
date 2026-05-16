package CrimeDispatch;

public interface Graph<V, E> {

    // Add/Remove vertices
    void addVertex(V v);
    void removeVertex(V v);

    // Add/remove edges
    void addEdge(E e);
    void removeEdge(E e);

    // Query operations
    void containsVertex(V v);
    void containsEdge(E e);

    // Traversal operations
    V[] neighbors(V v);
}
