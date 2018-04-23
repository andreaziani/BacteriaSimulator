package model.food.insertionstrategy.position;

import java.util.Random;

import org.apache.commons.math3.distribution.GeometricDistribution;

import model.state.Position;
import model.state.PositionImpl;

/**
 * Implementation of the strategy that uses geometric distribution.
 */
public final class GeometricDistributionStrategy implements PositionStrategy {
    private static final double PROBABILITY = 0.01;
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     */
    public GeometricDistributionStrategy(final Position maxPosition) {
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final Random rand = new Random();
        final GeometricDistribution dist = new GeometricDistribution(PROBABILITY);
        return new PositionImpl(
                rand.nextBoolean() ? (dist.sample() + (int) (maxPosition.getX() / 2) % maxPosition.getX())
                        : (maxPosition.getX() / 2) - dist.sample(),
                rand.nextBoolean() ? (dist.sample() + (int) (maxPosition.getY() / 2) % maxPosition.getY())
                        : (maxPosition.getY() / 2) - dist.sample());
    }
}
