package CrimeDispatch;

import java.nio.file.Path;
import java.util.*;

/**
 * A generic implementation of Dijkstra's algorithm to compute shortest routes
 * across the city road network infrastructure.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class Dijkstra<V extends CrimeDispatch.Vertex, E extends CrimeDispatch.Edge<V, W>, W extends Comparable<W>> {

    // The backing directed graph representation
    private final CrimeDispatch.DirectedWeightedGraph<V, E, W> graph;

    /**
     * Constructs a Dijkstra pathfinder instance for a given graph topology.
     *
     * @param graph The directed weighted graph to evaluate.
     */
    public Dijkstra(CrimeDispatch.DirectedWeightedGraph<V, E, W> graph) {
        this.graph = graph;
    }

    /**
     * An internal wrapper to handle distances within the execution priority queue.
     */
    private class PathNode implements Comparable<PathNode> {
        final V vertex;
        final double distance;

        PathNode(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(PathNode other) {
            // To allow sorting of vertices within the priority queue in ascending order
            return Double.compare(this.distance, other.distance);
        }
    }

    /**
     * Solves the shortest path from a source vertex to a destination vertex.
     *
     * @param from The starting node location.
     * @param to   The destination node target.
     * @return A sequential list tracking the optimal travel vertex sequence.
     */
    public List<CrimeDispatch.Vertex> findShortestRoute(V from, V to) {
        Map<CrimeDispatch.Vertex, Double> dist = new HashMap<>();
        // distance between source and 'u' vertex
        Map<CrimeDispatch.Vertex, CrimeDispatch.Vertex> prev = new HashMap<>();
        // previously found solutions
        // Using priority queue to sort through which is the shortest path node/vertex
        PriorityQueue<PathNode> toExplore = new PriorityQueue<>();
        // unexplored vertices

        // Initialise all vertices
        for (V vertex : graph.vertexSet()) {
            dist.put(vertex, Double.MAX_VALUE);
        }

        // set distance from source to be 0
        dist.replace(from, 0.0);
        // Add source node as starting node
        toExplore.add(new PathNode(from, 0.0));

        while (!toExplore.isEmpty()) {
            // grab vertex with smallest distance from source
            PathNode first = toExplore.poll();
            V u = first.vertex;
            double d = first.distance;

            // Don't explore more nodes as we already reached destination
            if(u.equals(to)) {
                break;
            }

            // go through all neighbors and update prev and dist
            for (E edge : graph.outgoingEdgesOf(u)) {
                V v = edge.getTarget();
                double alt  = dist.get(u) + graph.getEdgeWeight(edge);

                // Update dist if we find a better path
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);

                    // Add newer shorter path to queue
                    toExplore.offer(new PathNode(v, alt));
                }
            }
        }

        // return path
        List<CrimeDispatch.Vertex> path = new ArrayList<>();
        CrimeDispatch.Vertex current = to; // go in reverse
        while(current != null) {
            path.addFirst(current);
            // add to the start of path
            current = prev.get(current);
            // grabs previous node
        }

        return path;
    }

    /**
     * Calculates the cumulative weights across a parsed collection of route paths.
     *
     * @param path The ordered vertex collection list representing the route.
     * @return The aggregated weight total representing complete travel path distance.
     */
    public double getRouteDistance(List<CrimeDispatch.Vertex> path) {
        if (path == null || path.size() < 2) {
            return 0.0;
        }
        double totalDistance = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            // Use your graph's built-in structural lookup
            CrimeDispatch.Edge edge = graph.getEdge((V) path.get(i), (V) path.get(i + 1));
            if (edge != null) {
                totalDistance += graph.getEdgeWeight((E) edge);
            }
        }
        return totalDistance;
    }
}