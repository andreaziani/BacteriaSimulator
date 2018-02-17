package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
/** Manager that contains all the existent foods.
 * 
 *
 *
 */
public class ExistentFoodManagerImpl implements ExistentFoodManager {
    private final Map<String, Food> existentFoods = new HashMap<>();
    @Override
    public final Optional<Food> getFood(final String name) {
        if (this.existentFoods.containsKey(name)) {
            return Optional.of(this.existentFoods.get(name));
        }
        return Optional.empty();
    }

    @Override
    public final void addFood(final Food food) {
        this.existentFoods.putIfAbsent(food.getFoodName(), food);
    }

    @Override
    public final Set<Food> getExsistentFoods() {
       return this.existentFoods.values().stream().collect(Collectors.toSet());
    }

}
