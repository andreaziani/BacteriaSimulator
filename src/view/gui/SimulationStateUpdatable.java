package view.gui;

import controller.SimulationCondition;

/**
 * Represents an object that can be notified for the change of a
 * SimulationState. To ensure correctness in all situations, all implementations
 * of this interface that operate on the GUI must be thread safe.
 */
@FunctionalInterface
public interface SimulationStateUpdatable {
    /**
     * Update the simulation state. It is thread safe in a correct implementation of
     * this interface.
     * 
     * @param state
     *            the simulation state updated.
     */
    void updateSimulationState(SimulationCondition state);
}
