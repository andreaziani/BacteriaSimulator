package model.food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.exceptions.AlreadyExistingFoodException;

/**
 * Manager that contains all the existing types of food.
 *
 */
public class ExistingFoodManagerImpl implements ExistingFoodManager {
    private final List<Food> existingFoods = new ArrayList<>();
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
    public List<Food> getExistingFoodsSet() {
        return Collections.unmodifiableList(this.existingFoods);
    }

}
