package view.food;

import java.util.Set;

import model.food.Nutrient;

/**
 * 
 * The interface representing a food in the view.
 *
 */
public interface ViewFood {
    /**
     * 
     * @return the name of the food.
     */
    String getName();
    /**
     * 
     * @return an unmodifiable copy of the nutrients that compose the food.
     */
    Set<Nutrient> getNutrients();
    /**
     * 
     * @param nutrient of which return the quantity.
     * @return the quantity of the nutrient in the food.
     */
    double getQuantityFromNutrient(Nutrient nutrient);
}
