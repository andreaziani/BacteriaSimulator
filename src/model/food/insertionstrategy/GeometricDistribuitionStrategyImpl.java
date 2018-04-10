package model.food.insertionstrategy;

import org.apache.commons.math3.distribution.GeometricDistribution;

import model.Position;
import model.PositionImpl;

/**
 * Implementation of the strategy that uses a geometric distribution for the
 * random choice.
 */
public class GeometricDistribuitionStrategyImpl implements RandomPositionStrategy {
    private static final double PROBABILITY = 0.01;
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     */
    public GeometricDistribuitionStrategyImpl(final Position maxPosition) {
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final GeometricDistribution dist = new GeometricDistribution(PROBABILITY);
        return new PositionImpl(dist.sample() % maxPosition.getX(), dist.sample() % maxPosition.getY());
    }
}
