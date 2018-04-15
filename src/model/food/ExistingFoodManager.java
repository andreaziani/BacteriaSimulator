package model.food;

import java.util.List;

import utils.exceptions.AlreadyExistingFoodException;

/**
 * Manager that contains all existing type of foods created.
 * 
 *
 */
public interface ExistingFoodManager {
    /**
     * Allows to add a new type of food in the manager.
     * 
     * @param food
     *            the new type of food to add to the manager.
     * @throws AlreadyExistingFoodException
     *             if the food already exist.
     */
    void addFood(Food food);

    /**
     * Get all types of already existing food.
     * 
     * @return an unmodifiable copy of the list of existing foods.
     */
    List<Food> getExistingFoodsSet();
}
