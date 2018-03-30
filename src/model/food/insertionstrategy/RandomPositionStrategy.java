package model.food.insertionstrategy;

import model.Position;

/**
 * Interface to take a position randomly.
 *
 */
public interface RandomPositionStrategy {
    /**
     * Get a random position.
     * 
     * @return the position chosen randomly.
     */
    Position getPosition();
}
