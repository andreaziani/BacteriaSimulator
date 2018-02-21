package model.food;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AlreadyExistingFoodException;
/** 
 * Manager that contains all the existing foods.
 *
 */
public class ExistingFoodManagerImpl implements ExistingFoodManager {
    private final Map<String, Food> existingFoods = new HashMap<>();
    @Override
    public void addFood(final String name, final Food food) {
        if (!this.existingFoods.containsKey(name)) {
            this.existingFoods.put(name, food); // if someone add 2 foods with same name, the first only will be saved.
        } else {
            throw new AlreadyExistingFoodException();
        }
    }

    @Override
    public Set<Food> getExistingFoodsSet() {
       return Collections.unmodifiableSet(this.existingFoods.values().stream()
                                                                     .collect(Collectors.toSet()));
    }

    @Override
    public Map<String, Food> getExistingFoodsMap() {
        return Collections.unmodifiableMap(this.existingFoods);
    }

}
