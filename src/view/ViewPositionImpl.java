package view;

import utils.Pair;

/**
 * Class to manage the coordinates
 * of bacteria and food in the view. 
 * 
 */
public class ViewPositionImpl implements ViewPosition {
    private final Pair<Double, Double> position;
    /**
     * Constructor of position from two double coordinates.
     * @param x the first coordinate.
     * @param y the second coordinate.
     */
    public ViewPositionImpl(final double x, final double y) {
        this.position = new Pair<>(x, y);
    }
    @Override
    public double getX() {
        return this.position.getFirst();
    }

    @Override
    public double getY() {
        return this.position.getSecond();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
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
        ViewPositionImpl other = (ViewPositionImpl) obj;
        if (position == null) {
            if (other.position != null) {
                return false;
            }
        } else if (!position.equals(other.position)) {
            return false;
        }
        return true;
    }

}
