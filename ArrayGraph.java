package CrimeDispatch;

public class ArrayGraph<V, E> implements Graph<V, E> {

    public class Vertex<T> {

        // aka value, which denotes the element stored inside vertex, in our case that's the place name (e.g. "Aston")
        public T name;

        public Vertex(T name) {
            this.name = name;
        }

        public T getName() { return this.name; }
    }

    // Making this a weighted graph

    public class Edge<Weight> { // Making this generic as weight can be many things, e.g. float, int, uint8

        Weight distance;

        public Edge(V Va, V Vb, Weight distance) { // Alternatively Va = from, Vb = to
            this.distance = distance;
        }
    }

    @Override
    public boolean addVertex(V v) {
        return false;
    }

    @Override
    public boolean removeVertex(V v) {
        return false;
    }

    @Override
    public boolean addEdge(E e) {
        return false;
    }

    @Override
    public boolean removeEdge(E e) {
        return false;
    }

    @Override
    public boolean containsVertex(V v) {
        return false;
    }

    @Override
    public boolean containsEdge(E e) {
        return false;
    }

    @Override
    public V[] neighbors(V v) {
        return null;
    }
}
