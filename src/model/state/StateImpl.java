package model.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import model.bacteria.Bacteria;
import model.food.Food;

/**
 * State implementation.
 * 
 *
 */
public class StateImpl implements State {
    private final Map<? extends Position, ? extends Food> foodState;
    private final Map<? extends Position, ? extends Bacteria> bacteriaState;

    /**
     * 
     * @param foodState
     *            the state of foods the in environment.
     * @param bacteriaState
     *            the state of bacteria in the environment.
     */
    public StateImpl(final Map<? extends Position, ? extends Food> foodState,
            final Map<? extends Position, ? extends Bacteria> bacteriaState) {
        this.foodState = new HashMap<>(foodState);
        this.bacteriaState = new HashMap<>(bacteriaState);
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
