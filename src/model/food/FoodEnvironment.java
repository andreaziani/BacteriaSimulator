package model.food;

import java.util.Map;

import model.Position;


/** 
 * Environment that deals with food operations, 
 * allows to enter new food or change its status.
 *
 *
 */
public interface FoodEnvironment {
    /**
     * 
     * @param food to be add in the environment.
     * @param position of the food in the environment.
     * @throws PositionAlreadyOccupiedException if the position is occupied.
     */
    void addFood(Food food, Position position);
    /**
     * 
     * @param oldPosition of the food, to be changed.
     * @param newPosition of the food.
     * @param food that changes position.
     * @throws RuntimeException if the declaration of food isn't correct.
     */
    void changeFoodPosition(Position oldPosition, Position newPosition, Food food);
    /**
     * 
     * @param position of the food that will be removed.
     * @param food to be removed from the enviroment.
     */
    void removeFood(Food food, Position position);
    /**
     * 
     * @return an unmodifiable copy of a map that contains positions and foods.
     */
    Map<Position, Food> getFoodsState();

}
