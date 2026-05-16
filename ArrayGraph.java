package CrimeDispatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArrayGraph<V, E> implements Graph<V, E> {

    public class Vertex<T> {

        // aka value, which denotes the element stored inside vertex, in our case that's the place name (e.g. "Aston")
        public T name;

        public Vertex(T name) {
            setName(name);
        }

        public T getName() { return this.name; }
        public void setName(T name) { this.name = name; }
    }

    // Making this a weighted graph

    public class Edge<Weight> { // Making this generic as weight can be many things, e.g. float, int, uint8

        Weight distance;
        V Va;
        V Vb;

        public Edge(V Va, V Vb, Weight distance) { // Alternatively Va = from, Vb = to
            setVertices(Va, Vb);
            this.distance = distance;
        }

        public Set<V> getVertices() {
            Set<V> vertices =  new HashSet<>();
            vertices.add(Va);
            vertices.add(Vb);

            return vertices;
        }

        public void setVertices(V Va, V Vb) {
            this.Va = Va;
            this.Vb = Vb;
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
    public boolean containsVertex(V v) {
        ;
    }

    @Override
    public boolean containsEdge(E e) {
        ;
    }

    @Override
    public Set<V> neighbors(V v) {
        return null;
    }
}
