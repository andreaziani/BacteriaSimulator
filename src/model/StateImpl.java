package model;

import java.util.Collections;
import java.util.Map;

import model.food.Food;
import model.food.FoodEnvironment;
/**
 * State implementation.
 * 
 *
 */
public class StateImpl implements State {
    private final FoodEnvironment foodEnv;
    /**
     * 
     * @param foodEnv environment that contains the information of foods.
     */
    public StateImpl(final FoodEnvironment foodEnv) {
        this.foodEnv = foodEnv;
    }
    @Override
    public Map<Position, Food> getFoodsState() {
        return  Collections.unmodifiableMap(this.foodEnv.getFoodsState());
    }

}
