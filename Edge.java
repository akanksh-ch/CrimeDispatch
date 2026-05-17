package CrimeDispatch;

public class Edge<V, W> {
    public V Va;
    public V Vb;

    public W weight; // Weight can be generic, e.g. double, int, etc.

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

    public void setWeight(W weight) {
        this.weight = weight;
    }
}