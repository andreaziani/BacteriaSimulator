package view.model.food;

import java.awt.Color;
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
    /**
     * 
     * @return the color of the food.
     */
    Color getColor();
}
