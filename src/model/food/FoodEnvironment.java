package model.food;

import java.util.Map;

import model.Position;

/**
 * Environment that deals with food operations, allows to enter new food or
 * change its status.
 *
 *
 */
public interface FoodEnvironment {
    /**
     * Add a type of food to a specific location.
     * 
     * @param food
     *            to be add in the environment.
     * @param position
     *            of the food in the environment.
     * @throws PositionAlreadyOccupiedException
     *             if the position is occupied.
     */
    void addFood(Food food, Position position);

    /**
     * Add a random food taken from the manager of the existing foods in a random
     * position. If the food is placed in an already occupied position (from foods),
     * try entering again up to the maximum number of attempts specified in the
     * class.
     */
    void addRandomFood();

    /**
     * Change the position of a food.
     * 
     * @param oldPosition
     *            position of the food, to be changed.
     * @param newPosition
     *            new position of the food.
     * @param food
     *            the food that changes position.
     * @throws RuntimeException
     *             if the declaration of food isn't correct.
     * @throws PositionAlreadyOccupiedException
     *             if the new position is occupied by another food.
     */
    void changeFoodPosition(Position oldPosition, Position newPosition, Food food);

    /**
     * Remove a food from his position.
     * 
     * @param position
     *            the position of the food that will be removed.
     * @param food
     *            the food to be removed from the environment.
     * @throws IllegalArgumentException
     *             if this food isn't present at this position.
     */
    void removeFood(Food food, Position position);

    /**
     * 
     * @return an unmodifiable copy of a map that contains positions and foods.
     */
    Map<Position, Food> getFoodsState();
}
