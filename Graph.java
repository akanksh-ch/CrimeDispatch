package CrimeDispatch;

import java.util.Set;

public interface Graph<V, E> {

    // add vertex to graph
    boolean addVertex(V v);

    // check if vertex exists
    boolean containsVertex(V v);

    // get all vertices
    Set<V> vertexSet();

    // remove vertex from graph
    boolean removeVertex(V v);

    // add edge to graph
    boolean addEdge(V sourceVertex, V targetVertex, E e);

    // check if edge exists
    boolean containsEdge(E e);

    // find edge between two vertices
    E getEdge(V sourceVertex, V targetVertex);

    // get all edges
    Set<E> edgeSet();

    // remove edge from graph
    boolean removeEdge(E e);

    // get where the edge starts
    V getEdgeSource(E e);

    // get where the edge ends
    V getEdgeTarget(E e);

    // get edge weight
    double getEdgeWeight(E e);

    // set edge weight
    void setEdgeWeight(E e, double weight);

    // get edges pointing away from vertex
    Set<E> outgoingEdgesOf(V vertex);

    // get edges pointing towards vertex
    Set<E> incomingEdgesOf(V vertex);

}