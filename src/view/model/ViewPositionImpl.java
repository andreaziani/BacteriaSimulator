package view.model;

import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;


/**
 * Class to manage the coordinates of bacteria and food in the view.
 * 
 */
public final class ViewPositionImpl implements ViewPosition {
    private final Pair<Double, Double> coordinates;

    /**
     * Constructor of position from two double coordinates.
     * 
     * @param x
     *            the first coordinate.
     * @param y
     *            the second coordinate.
     */
    public ViewPositionImpl(final double x, final double y) {
        this.coordinates = Pair.of(x, y);
    }

    @Override
    public double getX() {
        return this.coordinates.getLeft();
    }

    @Override
    public double getY() {
        return this.coordinates.getRight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            final ViewPositionImpl other = (ViewPositionImpl) obj;
            if (other.coordinates != null) {
                return Objects.equals(coordinates, other.coordinates);
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return " position coordinates= (" + coordinates.getLeft() + " " + coordinates.getRight() + ") ";
    }

}
