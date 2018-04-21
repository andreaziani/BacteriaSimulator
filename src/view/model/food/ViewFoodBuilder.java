package view.model.food;

import org.apache.commons.lang3.tuple.Pair;
import model.food.Nutrient;

/**
 * Builder for ViewFoods.
 *
 */
public interface ViewFoodBuilder {
    /**
     * Add nutrient in the food to build.
     * 
     * @param nutrients
     *            the nutrient and his quantity.
     * @return the ViewFoodBuilder.
     */
    ViewFoodBuilder addNutrient(Pair<Nutrient, Double> nutrients);

    /**
     * Build the food.
     * 
     * @return the food builded.
     */
    ViewFood build();
}
