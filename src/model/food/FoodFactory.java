package model.food;

import java.util.Map;

/** 
 * A factory to create some different type of food.
 * 
 *
 */
public interface FoodFactory {
    /**
     * 
     * @param name of the food.
     * @param nutrients type and quantity of nutrients that compose a new food.
     * @return the food created.
     */
    Food createFoodFromNameAndNutrients(String name, Map<Nutrient, Double> nutrients);
    /**
     * Return a new Food with empty name.
     * @param nutrients type and quantity of nutrients that compose a new food.
     * @return the food created.
     */
    Food createFoodFromNutrients(Map<Nutrient, Double> nutrients);
}
