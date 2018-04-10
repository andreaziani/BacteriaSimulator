package model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import controller.SimpleState;
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
    
    @Override
    public int hashCode() {
        return Objects.hash(bacteriaState, foodState);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final StateImpl other = (StateImpl) obj;
        return this.bacteriaState.entrySet().containsAll(other.bacteriaState.entrySet())
                && other.bacteriaState.entrySet().containsAll(this.bacteriaState.entrySet())
                && this.foodState.entrySet().containsAll(other.foodState.entrySet())
                && other.foodState.entrySet().containsAll(this.foodState.entrySet());
    }
}
