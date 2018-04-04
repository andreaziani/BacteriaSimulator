package view.model;

import java.util.Collections;
import java.util.Map;

import view.model.bacteria.ViewBacteria;
import view.model.food.ViewFood;

/**
 * Implementation of ViewState.
 *
 */
public class ViewStateImpl implements ViewState {
    private final Map<ViewPosition, ViewFood> foodsState;
    private final Map<ViewPosition, ViewBacteria> bacteriaState;

    /**
     * Constructor that create a ViewState from foodstate and bacteriastate.
     * 
     * @param foodsState
     *            state of foods present in simulation.
     * @param bacteriaState
     *            state of bacteria present in simulation.
     */
    public ViewStateImpl(final Map<ViewPosition, ViewFood> foodsState,
            final Map<ViewPosition, ViewBacteria> bacteriaState) {
        this.foodsState = foodsState;
        this.bacteriaState = bacteriaState;
    }

    @Override
    public Map<ViewPosition, ViewFood> getFoodsState() {
        return Collections.unmodifiableMap(this.foodsState);
    }
}
