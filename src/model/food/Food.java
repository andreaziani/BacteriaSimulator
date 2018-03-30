package model.food;

import java.util.Set;

import model.simulator.Collidable;

/**
 * The interface representing a food in the simulation. A Food is composed by a
 * name and nutrients in their quantity.
 */
public interface Food extends Collidable {
    /**
     * 
     * @return the name of the food.
     */
    String getName();

    /**
     * Get all the nutrients present in the food.
     * 
     * @return a set of nutrients present in the food.
     */
    Set<Nutrient> getNutrients();

    /**
     * Given a nutrient returns his quantity.
     * 
     * @param nutrient
     *            the nutrient of which want the quantity.
     * @return the quantity of nutrient in the food.
     */
    double getQuantityFromNutrient(Nutrient nutrient);
}
