package view;

import view.model.ViewState;

/**
 * Interface that represents the access point for the Simulation's Controller to
 * the View.
 */
public interface View extends SimulationStateUpdatable {
    /**
     * Update view state.
     * 
     * @param state
     *            of view.
     */
    void update(ViewState state);
    /**
     * Update the list of existing foods.
     */
    void updateExistingFoods();
}
