package model.food.insertionstrategy;

import model.food.ExistingFoodManager;
import model.food.Food;

/**
 * Interface for adding foods randomly taken from existing foods.
 *
 */
public interface RandomFoodStrategy {
    /**
     * Get a random food taken from ExistingFoodManager.
     * 
     * @param manager
     *            the manager from which to take foods.
     * @return the food chose randomly.
     */
    Food getFood(ExistingFoodManager manager);
}
