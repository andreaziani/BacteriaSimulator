package controller.food;


import java.util.Set;

import view.ViewPosition;
import view.food.ViewFood;

/**
 * 
 * Controller that deals with interactions between foods.
 * Food declared in view will be matched to model foods,
 * also generates food at each round following an exponential distribution.
 *
 */
public interface FoodController {
    /**
     * Add a food added by user.
     * @param food viewfood to add.
     * @param position of the food in the view.
     */
    void addFoodFromViewToModel(ViewFood food, ViewPosition position);
    /**
     * 
     * @return a unmodifiable set of existing view foods.
     */
    Set<ViewFood> getExistingViewFoods();
}
