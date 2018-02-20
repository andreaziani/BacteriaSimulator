package model.food;

import java.util.Optional;
import java.util.Set;

import utils.AlreadyExistingFoodException;

/** 
 * Manager that contains all existent type of foods created.
 * 
 *
 */
public interface ExistentFoodManager {
    /** 
     * Allows to get a food if present in the manager.
     * @param name of the food to be getted.
     * @return a optional that contains the food if is present.
     */
    Optional<Food> getFood(String name);
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
    Set<Food> getExsistentFoods();
}
