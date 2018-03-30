package controller.food;

import java.util.Set;

import view.model.ViewPosition;
import view.model.food.ViewFood;

/**
 * 
 * Controller that deals with interactions between foods. ViewFood declared in
 * view will be matched to model Food.
 *
 */
public interface FoodController {
    /**
     * Add a type of food from the view to model into a specific location.
     * 
     * @param food
     *            the type of Food to add.
     * @param position
     *            the location of the food.
     * @throws PositionAlreadyOccupiedException
     *             if the position is already occupied.
     */
    void addFoodFromViewToModel(ViewFood food, ViewPosition position);

    /**
     * Get all types of already existing food.
     * 
     * @return an unmodifiable set with all types of food.
     */
    Set<ViewFood> getExistingViewFoods();

    /**
     * Adds a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to be added.
     * @throws AlreadyExistingFoodException
     *             if the food is already existing.
     */
    void addNewTypeOfFood(ViewFood food);
}
