package controller.food;

import java.awt.Color;
import java.util.List;

import model.Position;
import model.food.Food;
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
    void addFoodFromViewToModel(ViewFood food, Position position);

    /**
     * Get all types of already existing food.
     * 
     * @return an unmodifiable list with all types of food.
     */
    List<ViewFood> getExistingViewFoods();

    /**
     * Adds a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to be added.
     * @throws AlreadyExistingFoodException
     *             if the food is already existing.
     */
    void addNewTypeOfFood(ViewFood food);

    /**
     * Get the color of a food.
     * 
     * @param food
     *            the food of which want the color.
     * @return the color of the food.
     */
    Color getColorFromFood(Food food);
}
