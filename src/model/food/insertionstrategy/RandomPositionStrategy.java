package model.food.insertionstrategy;

import model.Position;

/**
 * Interface for strategies of adding in a random position the food.
 *
 */
public interface RandomPositionStrategy {
    /**
     * 
     * @return position of foods.
     */
     Position getPosition();
}
