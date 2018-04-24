package model.perception;

import java.util.Optional;
import java.util.Map;
import model.Direction;
import model.food.Food;

/**
 * Implementation of Perception interface.
 *
 */
public final class PerceptionImpl implements Perception {
    private final Optional<Food> foodInPosition;
    private final Map<Direction, Double> distsToFood;

    /**
     * Constructor for the Perception class.
     * @param foodInPosition Optional<Food> that indicate whether a food is present in the given Position
     * @param distsToFood Map that associate to every direction the minimum distance to Food, no distance if there is no Food
     */
    public PerceptionImpl(final Optional<Food> foodInPosition, final Map<Direction, Double> distsToFood) {
        this.foodInPosition = foodInPosition;
        this.distsToFood = distsToFood;
    }

    @Override
    public Optional<Food> getFood() {
        return this.foodInPosition;
    }

    @Override
    public Optional<Double> distFromFood(final Direction dir) {
        if (this.distsToFood.containsKey(dir)) {
            return Optional.of(this.distsToFood.get(dir));
        } else {
            return Optional.empty();
        }
    }
}
