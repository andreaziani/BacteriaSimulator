package model.food.insertionstrategy;
import java.util.Random;

import org.uncommons.maths.random.ExponentialGenerator;

import model.Position;
import model.PositionImpl;

/**
 * Implementation of the random adding strategies. 
 *
 */
public class ExponentialDistribuitionStrategyImpl implements RandomPositionStrategy {
    private static final double EXPONENTIAL_RATE = 0.1;
    @Override
    public Position getPosition() {
        final Random rand = new Random();
        final ExponentialGenerator generator = new ExponentialGenerator(EXPONENTIAL_RATE, rand);
        return new PositionImpl(generator.nextValue(), generator.nextValue());
    }
}
