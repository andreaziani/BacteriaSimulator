package model.food;

import java.util.Set;

import model.simulator.Collidable;

/** 
 * The interface representing a food in the simulation.
 * 
 */
public interface Food extends Collidable {
    /**
     * 
     * @return the name of the food.
     */
    String getName();
    /** 
     * 
     * @return a set of nutrients present in the food.
     */
    Set<Nutrient> getNutrients();
    /**
     * 
     * @param nutrient is the nutrient to consider.
     * @return quantity the quantity of nutrient in the food. 
     * (if the nutrient isn't present in the food return 0.0).
     */
    double getQuantityFromNutrient(Nutrient nutrient);
}
