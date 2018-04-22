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
public final class RandomPositionStrategy implements PositionStrategy {
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment..
     * @throws IllegalArgumentException
     *             if the strategy chosen is not correct.
     */
    public RandomPositionStrategy(final Position maxPosition) {
        super();
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final Random rand = new Random();
        return new PositionImpl(rand.nextInt((int) maxPosition.getX()), rand.nextInt((int) maxPosition.getY()));
    }

}
