package model;

import java.util.HashSet;

import java.util.Set;

import utils.Pair;

/** 
 * Implementation of FoodEnvironment, contains information
 * about foods (Food's Position).
 *
 *
 */
public class FoodEnvironmentImpl implements FoodEnvironment {
    private final Set<Pair<Food, Position>> foodInserted = new HashSet<>();
    @Override
    public void addFood(final Food food, final Position position) {
        this.foodInserted.add(new Pair<Food, Position>(food, position));
    }
    @Override
    public void removeFood(final Food food, final Position position) {
        this.foodInserted.remove(new Pair<>(position, food));
    }
    @Override
    public Set<Pair<Food, Position>> getFoodsState() {
        return this.foodInserted;
    }
    @Override
    public void changeFoodPosition(final Position oldPosition, final Position newPosition, final Food food) {
        if (this.foodInserted.contains(new Pair<>(food, oldPosition))) {
            removeFood(food, oldPosition);
            addFood(food, newPosition);
        }
    }

}
