package CrimeDispatch;

import java.util.HashSet;
import java.util.Set;

public class Pair<V> { // To model a pair of Vertices, hence using only type

    V Va;
    V Vb;

    public Pair(V Va, V Vb) {
        this.setPair(Va, Vb);
    }

    public void setPair(V Va, V Vb) {
        this.Va = Va;
        this.Vb = Vb;
    }

    public Set<V> getPair() {
        Set<V> values = new HashSet<>();
        values.add(Va);
        values.add(Vb);

        return values;
    }
}