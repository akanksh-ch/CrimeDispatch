package CrimeDispatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedWeightedGraph<V,E extends CrimeDispatch.Edge, W extends Comparable<W>> implements CrimeDispatch.WeightedGraph<V, E, W> { // ensuring Edge has neccessary functions despite being generic

    // Using helper class to differentiate direction of edges
    public class EdgeContainer<E> {

        // Using final to prevent funky business with initialisation
        public final Set<E> incoming = new HashSet<>();
        public final Set<E> outgoing = new HashSet<>();

        // Add to edges
        public EdgeContainer(E incomingEdge, E outgoingEdge) {
            incoming.add(incomingEdge);
            outgoing.add(outgoingEdge);
        }

        // Do not add anything if not mentioned (e.g. in case when vertexes are initialised without edges made)
        public EdgeContainer() {

        }
    }

    Map<V, EdgeContainer<E>> graph;

    public DirectedWeightedGraph() {
        this.graph = new HashMap<>();
    }

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

    public boolean addEdge(E e) {
        if (e == null) return false; // Can't add empty Edge

        // Check if vertices are valid
        if (!graph.containsKey(e.getSource()) || !graph.containsKey(e.getTarget())) {
            return false;
        }

        // insert them
        EdgeContainer<E> sourceContainer = graph.get(e.getSource());
        EdgeContainer<E> targetContainer = graph.get(e.getTarget());

        // update edge data
        sourceContainer.outgoing.add(e); // The edge leaves the source
        targetContainer.incoming.add(e); // The edge enters the target

        return true;
    }

    public boolean containsEdge(E e) {
        return graph.containsKey(e.getSource());
    }

    public E getEdge(V sourceVertex, V targetVertex) {
        EdgeContainer<E> sourceContainer = graph.get(sourceVertex);
        if (sourceContainer == null) { // when edge doesn't exist
            return null;
        }

        for (E edge: sourceContainer.outgoing) {
            if (edge.getTarget().equals(targetVertex)) {
                return edge;
            }
        }
        return null; // in case if statement doesn't work
    }

    public Set<E> edgeSet() {
        Set<E> allEdges = new HashSet<>();

        for (EdgeContainer container : graph.values()) {
            allEdges.addAll(container.outgoing); // declines duplicates
        }

        return allEdges;
    }

    public boolean removeEdge(E e) {

        EdgeContainer sourceContainer = graph.get(e.getSource());
        EdgeContainer targetContainer = graph.get(e.getTarget());

        if (sourceContainer == null || targetContainer == null) {
            return false;
        }

        // Making two booleans to track removal from both sides
        boolean removedFromSource = sourceContainer.outgoing.remove(e);
        boolean removedFromTarget = targetContainer.incoming.remove(e);

        return removedFromSource && removedFromTarget; // ensure only when both removed
    }

    public V getEdgeSource(E e) {
        return (V) e.getSource(); // should be fine as E extends Edge, type safety!
    }

    public V getEdgeTarget(E e) {
        return (V) e.getTarget(); // same as above
    }

    public double getEdgeWeight(E e) {
        return (double) e.weight;
    }

    public void setEdgeWeight(E e,W weight) {
        e.setWeight(weight);
    }

    public Set<E> outgoingEdgesOf(V vertex) {
        return graph.get(vertex).outgoing;
    }

    public Set<E> incomingEdgesOf(V vertex) {
        return graph.get(vertex).incoming;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        // https://stackoverflow.com/a/46908
        for(var entry : graph.entrySet()) {
            // Find all outgoing connections
            for (E edge : entry.getValue().outgoing) {
                // To test with https://graphonline.top/, Create Graph -> Using edge list

                result.append(String.format("%s-(%s)>%s\n", entry.getKey().toString(), edge.getWeightString(), edge.getTarget().toString()));
            }
        }

        return result.toString();
    }
}