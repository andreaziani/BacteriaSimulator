package view;

import org.apache.commons.lang3.tuple.Pair;

/**
 * The radius in the view.
 * 
 *
 */
public class Radius {
    private final Pair<Integer, Integer> coordinates;
    /**
     * 
     * @param x the radius in the x coordinate.
     * @param y the radius in the y coordinate.
     */
    public Radius(final int x, final int y) {
        this.coordinates = Pair.of(x, y);
    }
    /**
     * 
     * @return the radius in x coordinate.
     */
    public int getXRadius() {
        return this.coordinates.getLeft();
    }
    /**
     * 
     * @return the radius in y coordinate.
     */
    public int getYRadius() {
        return this.coordinates.getRight();
    }
}
