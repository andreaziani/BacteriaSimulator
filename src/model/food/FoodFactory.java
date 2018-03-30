package model.food;

import java.util.Map;

/**
 * Factory to create some different type of food.
 * 
 *
 */
public interface FoodFactory {
    /**
     * Create a food from name and nutrients.
     * 
     * @param name
     *            the name of the food.
     * @param nutrients
     *            all the types and quantity of nutrients that compose a new food.
     * @return the food created.
     */
    Food createFoodFromNameAndNutrients(String name, Map<Nutrient, Double> nutrients);

    /**
     * Create a food from his nutrients.
     * 
     * @param nutrients
     *            all the types and quantity of nutrients that compose a new food.
     * @return the food created.
     */
    Food createFoodFromNutrients(Map<Nutrient, Double> nutrients);
}
