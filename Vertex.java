package CrimeDispatch;

public class Vertex<T> {
    public T value;
    public Vertex(T value) { this.value = value;}

    @Override
    public String toString() {
        return value.toString();
    }
}
