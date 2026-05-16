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

        public Edge(Weight distance) { // Alternatively Va = from, Vb = to
            this.distance = distance;
        }
    }

    @Override
    public void addVertex(V v) {

    }

    @Override
    public void removeVertex(V v) {
        ;
    }

    @Override
    public void addEdge(E e) {
        ;
    }

    @Override
    public void removeEdge(E e) {
        ;
    }

    @Override
    public void containsVertex(V v) {
        ;
    }

    @Override
    public void containsEdge(E e) {
        ;
    }

    @Override
    public V[] neighbors(V v) {
        return null;
    }
}
