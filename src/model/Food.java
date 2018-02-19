package model;

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
     * @return quantity the quantity of nutrient in the food.
     */
    double getQuantityFromNutrients(Nutrient nutrient);
}
