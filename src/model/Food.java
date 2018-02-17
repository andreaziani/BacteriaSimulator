package model;

import java.util.Set;

/** Model Food.
 * 
 * 
 *
 */
public interface Food {
    /** Get all the Nutrients that are contained in the food.
     * 
     * @return Set of nutrients.
     */
    Set<Nutrients> getNutrients();
    /** Get how many nutrient is present in food.
     * 
     * @param nutrient 
     * @return double quantity of nutrient.
     */
    double getQuantityFromNutrients(Nutrients nutrient);
}
