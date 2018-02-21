package model.perception;

import java.util.Optional;

import model.Direction;
import model.food.Food;

/**
 * Interface representing a complete description of what a Bacteria can see from
 * the Environment. It can be extracted a food if there is one in the location
 * the bacteria is currently in.
 */
public interface Perception {
    /**
     * @return an optional containing the Food in the position from which this
     *         perception is originated or an empty optional if there is no food in
     *         that position.
     */
    Optional<Food> getFood();

    /**
     * Get the distance from the position of this perception to the nearest food in
     * a given direction.
     * 
     * @param dir
     *            a direction
     * @return an optional containing the distance to the nearest food or an empty
     *         optional if there is no food in that direction.
     */
    Optional<Double> distFromFood(Direction dir);
}
