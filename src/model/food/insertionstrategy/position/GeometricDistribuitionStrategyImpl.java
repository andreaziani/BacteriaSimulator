package model.food.insertionstrategy.position;

import org.apache.commons.math3.distribution.GeometricDistribution;

import model.Position;
import model.PositionImpl;

/**
 * Implementation of the strategy that uses a geometric distribution for the
 * random choice.
 */
public class GeometricDistribuitionStrategyImpl extends AbstractStrategy {
    private static final double PROBABILITY = 0.01;
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     * @param strategy
     *            the type of strategy chosen.
     */
    public GeometricDistribuitionStrategyImpl(final Position maxPosition, final DistributionStrategy strategy) {
        super();
        if (strategy != DistributionStrategy.GEOMETRIC_DISTRIBUTION) {
            throw new IllegalArgumentException();
        }
        this.maxPosition = maxPosition;
    }

    @Override
    protected final Position distributedPosition() {
        final GeometricDistribution dist = new GeometricDistribution(PROBABILITY);
        return new PositionImpl(dist.sample() % maxPosition.getX(), dist.sample() % maxPosition.getY());
    }
}
