package model.food;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.Position;
import utils.PositionAlreadyOccupiedException;

/** 
 * Implementation of FoodEnvironment, contains information
 * about foods (Food's Position).
 *
 *
 */
public class FoodEnvironmentImpl implements FoodEnvironment {
    private static final int MAXATTEMPS = 10;
    private final Map<Position, Food> foods = new HashMap<>();
    private final ExistingFoodManager manager;
    /**
     * Construct the FoodEnvironment from an ExistingFoodManager 
     * with which to know the types of food already created.
     * @param manager that contains all existing foods.
     */
    public FoodEnvironmentImpl(final ExistingFoodManager manager) {
        this.manager = manager;
    }

    @Override
    public void addFood(final Food food, final Position position) {
        if (!this.foods.containsKey(position)) {
            this.foods.put(position, food);
        } else {
            throw new PositionAlreadyOccupiedException();
        }
    }
    @Override
    public void removeFood(final Food food, final Position position) {
        if (this.foods.containsKey(position) && this.foods.get(position).equals(food)) {
            this.foods.remove(position);
        } else {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public Map<Position, Food> getFoodsState() {
        return Collections.unmodifiableMap(this.foods);
    }
    @Override
    public void changeFoodPosition(final Position oldPosition, final Position newPosition, final Food food) {
       removeFood(food, oldPosition); //maybe they can generate an Exception.
       addFood(food, newPosition);
    }
    //TODO impostare la dimensione massima dell'env.
    @Override
    public void addRandomFood() {
        boolean check = true;
        final RandomFoodStrategy foodStrategy = new RandomFoodStrategyImpl();
        final RandomPositionStrategy positionStrategy = new ExponentialDistribuitionStrategyImpl();
        for (int i = MAXATTEMPS; (i > 0 && check); i--) {
            try {
                addFood(foodStrategy.getFood(manager), positionStrategy.getPosition());
                check = false;
            } catch (PositionAlreadyOccupiedException e) {
                check = true;
            }
        }
    }

}
