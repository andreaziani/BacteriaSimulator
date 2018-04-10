package model.food.insertionstrategy.position;

import java.util.Random;

import model.Position;
import model.PositionImpl;

/**
 * Implementation of the strategy that use uniform distribution for the random
 * choise.
 * 
 *
 */
public class RandomPositionStrategyImpl extends AbstractStrategy {
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     * @param strategy
     *            the strategy chosen.
     */
    public RandomPositionStrategyImpl(final Position maxPosition, final DistributionStrategy strategy) {
        super(strategy);
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final Random rand = new Random();
        return new PositionImpl(rand.nextInt() % this.maxPosition.getX(), rand.nextInt() % this.maxPosition.getY());
    }

}
