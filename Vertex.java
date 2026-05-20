package CrimeDispatch;

import java.util.Objects;

/**
 * An abstraction representing an individual data point intersection within structural systems.
 *
 * @author Akanksh Chitimalla
 * @version 20/05/2026
 */
public class Vertex<T> {

    // Wrapped systemic item value
    public T value;

    /**
     * Injects an initialization context to configure internal generic fields.
     *
     * @param value The functional parameter context assigned to this location index.
     */
    public Vertex(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        // If the reference leads to same object then they're the same
        if (this == o) return true;
        // Check for incompatability
        if (o == null || getClass() != o.getClass()) return false;
        // Cast the other object to a Vertex
        Vertex<?> vertex = (Vertex<?>) o;
        // Compare the actual value
        return Objects.equals(value, vertex.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}