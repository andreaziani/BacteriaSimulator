package utils;

import java.util.Objects;

/** A class that contains a pair of objects.
 * 
 *
 *
 * @param <X> first object.
 * @param <Y> second object.
 */
public class Pair<X, Y> {
    private final X elem1;
    private final Y elem2;
    /**
     * 
     * @param elem1 first elem of the pair.
     * @param elem2 second elem of the pair.
     */
    public Pair(final X elem1, final Y elem2) {
        this.elem1 = elem1;
        this.elem2 = elem2;
    }
    /**
     * 
     * @return the first elem of the pair.
     */
    public X getFirst() {
        return elem1;
    }
    /**
     * 
     * @return the second elem of the pair.
     */
    public Y getSecond() {
        return elem2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.elem1, this.elem2);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            return Objects.equals(other.elem1, this.elem1) && Objects.equals(other.elem2, this.elem2);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "(" + this.elem1.toString() + ", " + this.elem2.toString() + ")";
    }
}
