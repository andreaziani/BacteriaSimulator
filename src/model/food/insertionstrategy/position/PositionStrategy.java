package model.food.insertionstrategy.position;

import model.state.Position;

/**
 * Interface to take a position randomly.
 *
 */
public interface PositionStrategy {
    /**
     * Get a random position.
     * 
     * @return the position chosen randomly.
     */
    Position getPosition();
}
