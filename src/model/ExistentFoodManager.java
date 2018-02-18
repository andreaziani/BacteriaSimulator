package model;

import java.util.Optional;
import java.util.Set;

/** Manager that contains all existent type of foods created.
 * 
 * 
 *
 */
public interface ExistentFoodManager {
    /** 
     * 
     * @param name of the food to be getted.
     * @return a optional that contains the food if is present.
     */
    Optional<Food> getFood(String name);
    /**
     * 
     * @param food to add to the manager.
     * @param name of food.
     */
    void addFood(String name, Food food);
    /**
     * 
     * @return a copy of the entire set of existent foods.
     */
    Set<Food> getExsistentFoods();
}
