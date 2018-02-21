package model.food;

import java.util.Map;
import java.util.Set;

/** 
 * Manager that contains all existing type of foods created.
 * 
 *
 */
public interface ExistingFoodManager {
    /** 
     * Get a map that contains all existent foods.
     * @return a map with couple name of food-correspondent food.
     */
    Map<String, Food> getExistingFoodsMap();
    /**
     * Allows to add a new food in the manager.
     * @param food to add to the manager.
     * @param name of food.
     * @throws AlreadyExistingFoodException 
     *          if someone tries to insert already existing food.
     */
    void addFood(String name, Food food);
    /**
     * 
     * @return an unmodifiable copy of the entire set of existent foods.
     */
    Set<Food> getExistingFoodsSet();
}
