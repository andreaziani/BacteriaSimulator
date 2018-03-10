package model;

import java.util.Objects;

import utils.Pair;


/**
 * Position of objects in the environment.
 * 
 *
 */
public class PositionImpl implements Position {
    private final Pair<Double, Double> coordinates;
    /**
     * Constructor of position from two double coordinates.
     * @param x the first coordinate.
     * @param y the second coordinate.
     */
    public PositionImpl(final double x, final double y) {
        this.coordinates = new Pair<>(x, y);
    }
    @Override
    public double getX() {
       return this.coordinates.getFirst();
    }

    @Override
    public double getY() {
        return this.coordinates.getSecond();
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

}
