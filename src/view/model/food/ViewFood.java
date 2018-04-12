package view.model.food;

import java.util.Set;

import model.food.Nutrient;
import view.gui.Colorable;

/**
 * 
 * The interface representing a food in the view. A Food is composed by a name,
 * nutrients in their quantity and color.
 *
 */
public interface ViewFood extends Colorable {
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
}
