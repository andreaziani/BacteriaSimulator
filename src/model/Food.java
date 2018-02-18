package model;

import java.util.Set;

/** Food that bacteria will eat.
 * 
 * 
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
