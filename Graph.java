package CrimeDispatch;

public interface Graph<V, E> {

    // Add/Remove vertices
    boolean addVertex(V v);
    boolean removeVertex(V v);

    // Add/remove edges
    boolean addEdge(E e);
    boolean removeEdge(E e);

    // Query operations
    boolean containsVertex(V v);
    boolean containsEdge(E e);

    // Traversal operations
    V[] neighbors(V v);
}
