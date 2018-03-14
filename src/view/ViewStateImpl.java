package view;

import java.util.Map;

import view.food.ViewFood;

/**
 * Implementation of ViewState.
 *
 */
public class ViewStateImpl implements ViewState {
    private Map<ViewPosition, ViewFood> foodsState;
    /**
     * Constructor that create a ViewState from foodstate and bacteriastate.
     * @param foodsState state of foods present in simulation.
     */
    public ViewStateImpl(final Map<ViewPosition, ViewFood> foodsState) { // TODO manca la mappa di posizione batterio.
        this.foodsState = foodsState;
    }
}
