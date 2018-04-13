package model.food.insertionstrategy.position;

import model.Position;
/**
 * Abstract implementation of Strategy.
 */
public abstract class AbstractStrategy implements RandomPositionStrategy {
    @Override
    public final Position getPosition() {
        return this.distributedPosition();
    }
    /**
     * Get a position from distribution strategy.
     * @return a position.
     */
    protected abstract Position distributedPosition();
}
