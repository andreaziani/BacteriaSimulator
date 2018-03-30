package model;

import java.util.Collections;
import java.util.Map;

import model.food.Food;

/**
 * State implementation.
 * 
 *
 */
public class StateImpl implements State {
    private final Map<Position, Food> foodState;

    /**
     * 
     * @param foodState
     *            the state of foods the in environment.
     */
    public StateImpl(final Map<Position, Food> foodState) { // TODO aggiungere lo stato dei batteri.
        this.foodState = foodState;
    }

    @Override
    public Map<Position, Food> getFoodsState() {
        return Collections.unmodifiableMap(this.foodState);
    }

}
