package model.food.insertionstrategy.position;

import org.apache.commons.math3.distribution.GeometricDistribution;

import model.state.Position;
import model.state.PositionImpl;

/**
 * Implementation of the strategy that uses a geometric distribution for the
 * random choice.
 */
public final class GeometricDistributionStrategy implements PositionStrategy {
    private static final double PROBABILITY = 0.01;
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     * @throws IllegalArgumentException
     *             if the strategy chosen is not correct.
     */
    public GeometricDistributionStrategy(final Position maxPosition) {
        super();
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final GeometricDistribution dist = new GeometricDistribution(PROBABILITY);
        return new PositionImpl(dist.sample() % maxPosition.getX(), dist.sample() % maxPosition.getY());
    }
}
