package view.gui;

import controller.SimulationState;

/**
 * 
 * 
 *
 */
public interface SimulationStateUpdatable {
    /**
     * Update the simulation state.
     * 
     * @param state
     *            the simulation state updated.
     */
    void updateSimulationState(SimulationState state);
}
