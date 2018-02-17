package model;

import java.util.Optional;
import java.util.Set;

/** Manager for existent foods.
 * 
 * 
 *
 */
public interface ExistentFoodManager {
    /** Get a food from his name if is present.
     * 
     * @param name of the food.
     * @return a optional that contains the food if is present.
     */
    Optional<Food> getFood(String name);
    /** Add a food to the manager.
     * 
     * @param food to add.
     * @param name of food.
     */
    void addFood(String name, Food food);
    /** Getter the entire set of existent foods.
     * 
     * @return a set of foods.
     */
    Set<Food> getExsistentFoods();
}
