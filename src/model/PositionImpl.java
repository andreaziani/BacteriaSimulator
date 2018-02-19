package model;

import utils.Pair;

/**
 * Position of objects in the environment.
 * 
 *
 */
public class PositionImpl implements Position {
    private Pair<Double, Double> coordinates;
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
       return this.coordinates.getElem1();
    }

    @Override
    public double getY() {
        return this.coordinates.getElem2();
    }

}
