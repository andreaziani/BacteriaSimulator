package model.food;

import java.util.Set;

/** 
 * Manager that contains all existing type of foods created.
 * 
 *
 */
public interface ExistingFoodManager {
    /**
     * Allows to add a new type of food in the manager.
     * @param food to add to the manager.
     * @throws AlreadyExistingFoodException 
     *          if someone tries to insert already existing food.
     */
    void addFood(Food food);
    /**
     * 
     * @return an unmodifiable copy of the entire set of existent foods.
     */
    Set<Food> getExistingFoodsSet();
}
