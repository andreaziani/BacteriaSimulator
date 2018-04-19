package model.food.insertionstrategy.position;

import java.util.Random;

import model.state.Position;
import model.state.PositionImpl;

/**
 * Implementation of the strategy that use uniform distribution for the random
 * choise.
 * 
 *
 */
public final class RandomPositionStrategy extends AbstractPositionStrategy {
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     * @param strategy
     *            the strategy chosen.
     * @throws IllegalArgumentException
     *             if the strategy chosen is not correct.
     */
    public RandomPositionStrategy(final Position maxPosition, final DistributionStrategy strategy) {
        super();
        if (strategy != DistributionStrategy.UNIFORM_DISTRIBUTION) {
            throw new IllegalArgumentException();
        }
        this.maxPosition = maxPosition;
    }

    @Override
    protected Position distributedPosition() {
        final Random rand = new Random();
        return new PositionImpl(rand.nextInt((int) maxPosition.getX()), rand.nextInt((int) maxPosition.getY()));
    }

}
