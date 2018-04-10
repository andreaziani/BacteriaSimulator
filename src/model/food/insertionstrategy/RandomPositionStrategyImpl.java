package model.food.insertionstrategy;

import java.util.Random;
import java.util.RandomAccess;

import model.Position;
import model.PositionImpl;

/**
 * Implementation of the strategy that use uniform distribution for the random
 * choise.
 * 
 *
 */
public class RandomPositionStrategyImpl implements RandomPositionStrategy, RandomAccess {
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     */
    public RandomPositionStrategyImpl(final Position maxPosition) {
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final Random rand = new Random();
        return new PositionImpl(rand.nextInt() % this.maxPosition.getX(), rand.nextInt() % this.maxPosition.getY());
    }

}
