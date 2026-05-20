package CrimeDispatch;

/**
 * Models a directed connection running between two structural vertices inside a network graph.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class Edge<V, W extends Comparable<W>> {

    // Public vertex coordinate handles representing source and target anchors
    public V Va;
    public V Vb;

    // Weight can be generic, e.g. double, int, etc.
    public W weight;

    /**
     * Instantiates an active explicit connection link between nodes.
     *
     * @param Va     The starting source vertex point.
     * @param Vb     The tracking target destination node vertex.
     * @param weight The evaluation cost metrics applied to traversing this path.
     */
    public Edge(V Va, V Vb, W weight) {
        this.Va = Va;
        this.Vb = Vb;
        this.weight = weight;
    }

    public V getTarget() {
        return Vb;
    }

    public V getSource() {
        return Va;
    }

    public W getWeight() {
        return weight;
    }

    public String getWeightString() {
        return weight.toString();
    }

    public void setWeight(W weight) {
        this.weight = weight;
    }
}