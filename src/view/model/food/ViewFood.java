package view.model.food;

import java.awt.Color;
import java.util.Set;

import model.food.Nutrient;

/**
 * 
 * The interface representing a food in the view. A Food is composed by a name,
 * nutrients in their quantity and color.
 *
 */
public interface ViewFood {
    /**
     * Get the name of the food.
     * 
     * @return the name of the food.
     */
    String getName();

    /**
     * Get all the nutrients present in the food.
     * 
     * @return an unmodifiable copy of the nutrients that compose the food.
     */
    Set<Nutrient> getNutrients();

    /**
     * Get the quantity of one nutrient.
     * 
     * @param nutrient
     *            the nutrient of which return the quantity.
     * @return the quantity of the nutrient in the food.
     */
    double getQuantityFromNutrient(Nutrient nutrient);

    /**
     * Get the color of the food.
     * 
     * @return the color of the food.
     */
    Color getColor();
}
