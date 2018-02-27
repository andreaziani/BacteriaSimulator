package model.food;

import java.util.Set;

/** 
 * The interface representing a food in the simulation.
 *
 */
public interface Food {
    /** 
     * 
     * @return a set of nutrients present in the food.
     */
    Set<Nutrient> getNutrients();
    /**
     * 
     * @param nutrient the nutrient to consider
     * @return quantity the quantity of nutrient in the food 
     * (if the nutrient isn't present in the food return 0.0).
     */
    double getQuantityFromNutrient(Nutrient nutrient);
    /**
     * 
     * @return the radius of the food.
     */
    double getRadius();
}
