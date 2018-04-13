package model;

import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Position of objects in the environment.
 * 
 *
 */
public final class PositionImpl implements Position {
    private final Pair<Double, Double> coordinates;

    /**
     * Constructor of position from two double coordinates.
     * 
     * @param x
     *            the first coordinate.
     * @param y
     *            the second coordinate.
     */
    public PositionImpl(final double x, final double y) {
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
            final PositionImpl other = (PositionImpl) obj;
            if (other.coordinates != null) {
                return Objects.equals(coordinates, other.coordinates);
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Position:[" + coordinates.getLeft() + ", " + coordinates.getRight() + "]";
    }

}
