package model;

import java.util.Set;

/** Environment that deals with food operations.
 * 
 *
 *
 */
public interface FoodEnvironment {
    /**
     * 
     * @param food to be add in the environment.
     * @param position of the food in the environment.
     */
    void addFood(Food food, Position position);
    /**
     * 
     * @param oldPosition of the food, to be changed.
     * @param newPosition of the food.
     * @param food that changes position.
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
     * @return the set of information for every inserted food.
     */
    Set<Pair<Food, Position>> getFoodsState();

}
