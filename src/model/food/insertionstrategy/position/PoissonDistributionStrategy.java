package model.food.insertionstrategy.position;

import java.util.Random;

import org.apache.commons.math3.distribution.PoissonDistribution;
import model.state.Position;
import model.state.PositionImpl;

/**
 * 
 * Distribution strategy that use Poisson distribution.
 *
 */
public class PoissonDistributionStrategy implements PositionStrategy {
    private static final double MEAN = 200;
    private final Position maxPosition;

    /**
     * Build the strategy by passing the maximum position.
     * 
     * @param maxPosition
     *            the maximum position in the environment.
     */
    public PoissonDistributionStrategy(final Position maxPosition) {
        super();
        this.maxPosition = maxPosition;
    }

    @Override
    public Position getPosition() {
        final Random rand = new Random();
        final PoissonDistribution dist = new PoissonDistribution(MEAN);
        return new PositionImpl(
                rand.nextBoolean() ? (dist.sample() + (int) (maxPosition.getX() / 2) % maxPosition.getX())
                        : ((maxPosition.getX() / 2) - dist.sample()) % maxPosition.getX(),
                rand.nextBoolean() ? (dist.sample() + (int) (maxPosition.getY() / 2) % maxPosition.getY())
                        : ((maxPosition.getY() / 2) - dist.sample()) % maxPosition.getY());
    }

}
