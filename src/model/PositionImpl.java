package model;

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
        return coordinates.hashCode();
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
        final PositionImpl other = (PositionImpl) obj;
        if (coordinates == null) {
            if (other.coordinates != null) {
                return false;
            }
        } else if (!coordinates.equals(other.coordinates)) {
            return false;
        }
        return true;
    }

}
