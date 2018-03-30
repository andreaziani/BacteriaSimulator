package model.food;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import utils.exceptions.AlreadyExistingFoodException;

/**
 * Manager that contains all the existing types of food.
 *
 */
public class ExistingFoodManagerImpl implements ExistingFoodManager {
    private final Set<Food> existingFoods = new HashSet<>();
    private final Set<String> foodsNames = new HashSet<>();

    @Override
    public void addFood(final Food food) {
        if (!this.existingFoods.contains(food) && !this.foodsNames.contains(food.getName())) {
            this.foodsNames.add(food.getName());
            this.existingFoods.add(food);
        } else {
            throw new AlreadyExistingFoodException();
        }
    }

    @Override
    public Set<Food> getExistingFoodsSet() {
        return Collections.unmodifiableSet(this.existingFoods);
    }

}
