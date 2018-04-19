package controller.food;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import model.Environment;
import model.InteractiveEnvironment;
import model.state.Position;
import utils.ConversionsUtil;
import view.model.food.ViewFood;

/**
 * Implementation of FoodController, it manages food interactions.
 *
 */
public final class FoodControllerUtils {
    private FoodControllerUtils() {
    }

    /**
     * Add a type of food from the view to model into a specific location.
     * 
     * @param food
     *            the type of Food to add.
     * @param position
     *            the location of the food.
     * @param env
     *            the environment on which to add food.
     * @throws PositionAlreadyOccupiedException
     *             if the position is already occupied.
     */
    public static void addFoodFromViewToModel(final InteractiveEnvironment env, final ViewFood food,
            final Position position) {
        env.addFood(ConversionsUtil.viewFoodToFood(food), position);
    }

    /**
     * Get all types of already existing food.
     * 
     * @param env
     *            the environment from which to take food.
     * @return an unmodifiable list with all types of food.
     */
    public static List<ViewFood> getExistingViewFoods(final Environment env) {
        return Collections.unmodifiableList(env.getExistingFoods().stream()
                .map(food -> ConversionsUtil.foodToViewFood(food)).collect(Collectors.toList()));
    }

    /**
     * Adds a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to be added.
     * @param env
     *            the environment on which to add new type of food.
     * @throws AlreadyExistingFoodException
     *             if the food is already existing.
     */
    public static void addNewTypeOfFood(final InteractiveEnvironment env, final ViewFood food) {
        env.addNewTypeOfFood(ConversionsUtil.viewFoodToFood(food));
    }
}
