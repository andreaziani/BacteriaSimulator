package model;

import java.util.Collections;
import java.util.Map;

import model.bacteria.Bacteria;
import model.food.Food;

/**
 * State implementation.
 * 
 *
 */
public class StateImpl implements State {
    private final Map<Position, Food> foodState;
    private final Map<Position, Bacteria> bacteriaState;
    /**
     * 
     * @param foodState
     *            the state of foods the in environment.
     * @param bacteriaState
     *            the state of bacteria in the environment.
     */
    public StateImpl(final Map<Position, Food> foodState, final Map<Position, Bacteria> bacteriaState) {
        this.foodState = foodState;
        this.bacteriaState = bacteriaState;
    }

    @Override
    public Map<Position, Food> getFoodsState() {
        return Collections.unmodifiableMap(this.foodState);
    }

    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return Collections.unmodifiableMap(this.bacteriaState);
    }
}
