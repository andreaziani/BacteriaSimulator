package model.food.insertionstrategy.position;

import model.Position;
/**
 * Abstract implementation of Position Strategy.
 */
public abstract class AbstractPositionStrategy implements PositionStrategy {
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
