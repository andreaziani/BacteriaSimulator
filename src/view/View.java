package view;

import view.model.ViewState;

/**
 * Interface that represents the access point for the Simulation's Controller to
 * the View.
 */
public interface View {
    /**
     * Update view state.
     * 
     * @param state
     *            of view.
     */
    void update(ViewState state);
}
