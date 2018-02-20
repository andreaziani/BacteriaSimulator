package controller;

import view.ViewFood;

/**
 * 
 * Controller that deals with interactions between foods.
 * Food declared in view will be matched to model foods,
 * also generates food at each round following an exponential distribution.
 *
 */
public interface FoodController {
    /**
     * Add a food take from ExistingFoodManager.
     */
    void addRandomFood();
    /**
     * Add a food added by user.
     * @param food viewfood to add.
     */
    void addFoodFromView(ViewFood food);
    //TODO getNutrients();
    //TODO getViewFoods();
}
