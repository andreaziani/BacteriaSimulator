package model.food.insertionstrategy.position;
import org.apache.commons.math3.distribution.PoissonDistribution;
import model.state.Position;
import model.state.PositionImpl;

/**
 * 
 * Distribution strategy that use poisson distribution.
 *
 */
public class PoissonDistributionStrategy implements PositionStrategy {
    private static final double MEAN = 1;
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
        final PoissonDistribution dist = new PoissonDistribution(MEAN);
        return new PositionImpl(dist.sample() % maxPosition.getX(), dist.sample() % maxPosition.getY());
    }

}
