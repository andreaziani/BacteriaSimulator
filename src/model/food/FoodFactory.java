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
     * @param nutrients type and quantity of nutrients that compose a new food.
     * @return the food created.
     */
    Food createFoodFromNutrients(Map<Nutrient, Double> nutrients);
}
