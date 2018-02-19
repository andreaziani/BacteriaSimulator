package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AlreadyExistingFoodException;
/** Manager that contains all the existent foods.
 * 
 *
 *
 */
public class ExistentFoodManagerImpl implements ExistentFoodManager {
    private final Map<String, Food> existentFoods = new HashMap<>();
    @Override
    public Optional<Food> getFood(final String name) {
        if (this.existentFoods.containsKey(name)) {
            return Optional.of(this.existentFoods.get(name));
        }
        return Optional.empty();
    }

    @Override
    public void addFood(final String name, final Food food) {
        if (!this.existentFoods.values().contains(food) && !this.existentFoods.containsKey(name)) {
            this.existentFoods.put(name, food); // if someone add 2 foods with same name, the first only will be saved.
        } else {
            throw new AlreadyExistingFoodException();
        }
    }

    @Override
    public Set<Food> getExsistentFoods() {
       return this.existentFoods.values().stream().collect(Collectors.toSet()); // return a copy of the set of values.
    }

}
