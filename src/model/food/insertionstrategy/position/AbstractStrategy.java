package model.food.insertionstrategy.position;

import java.util.Objects;

import model.Position;
/**
 * Abstract implementation of Strategy.
 */
public abstract class AbstractStrategy implements RandomPositionStrategy {
    private final DistributionStrategy strategy;
    /**
     * 
     * @param strategy the strategy chosen.
     */
    public AbstractStrategy(final DistributionStrategy strategy) {
        this.strategy = strategy;
    }
    @Override
    public abstract Position getPosition();

    @Override
    public int hashCode() {
        return Objects.hash(strategy);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AbstractStrategy other = (AbstractStrategy) obj;
        return Objects.equals(this.strategy, other.strategy);
    }
}
