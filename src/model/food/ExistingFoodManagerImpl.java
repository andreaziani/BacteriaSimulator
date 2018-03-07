package model.food;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import utils.AlreadyExistingFoodException;
/** 
 * Manager that contains all the existing foods.
 *
 */
public class ExistingFoodManagerImpl implements ExistingFoodManager {
    private final  Set<Food> existingFoods = new HashSet<>();
    @Override
    public void addFood(final Food food) {
        if (!this.existingFoods.contains(food)) {
            this.existingFoods.add(food); // if someone add 2 foods with same name, the first only will be saved.
        } else {
            throw new AlreadyExistingFoodException();
        }
    }

    @Override
    public Set<Food> getExistingFoodsSet() {
       return Collections.unmodifiableSet(this.existingFoods);
    }

}
