package view.model;

import java.util.Collections;
import java.util.Map;

import view.model.food.ViewFood;

/**
 * Implementation of ViewState.
 *
 */
public class ViewStateImpl implements ViewState {
    private final Map<ViewPosition, ViewFood> foodsState;
    /**
     * Constructor that create a ViewState from foodstate and bacteriastate.
     * @param foodsState state of foods present in simulation.
     */
    public ViewStateImpl(final Map<ViewPosition, ViewFood> foodsState) { // TODO manca la mappa di posizione batterio.
        this.foodsState = foodsState;
    }

    @Override
    public Map<ViewPosition, ViewFood> getFoodsState() {
        return Collections.unmodifiableMap(this.foodsState);
    }
}
