package utils;
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

    //TODO non so se sia un buon hashcode.
    @Override
    public int hashCode() {
        return (elem1 == null ? 0 : elem1.hashCode()) ^ (elem2 == null ? 0 : elem2.hashCode());
    }
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (elem1 == null) {
            if (other.elem1 != null) {
                return false;
            }
        } else if (!elem1.equals(other.elem1)) {
            return false;
        }
        if (elem2 == null) {
            if (other.elem2 != null) {
                return false;
            }
        } else if (!elem2.equals(other.elem2)) {
            return false;
        }
        return true;
    }
}
