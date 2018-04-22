package controller;
/**
 * 
 * All possible state that simulation can have.
 *
 */
public enum SimulationCondition {
    /**
     * Simulation is not ready to start.
     */
    NOT_READY,
    /**
     * Simulation is ready to start.
     */
    READY,
    /**
     * Simulation is running.
     */
    RUNNING,
    /**
     * Simulation is in pause.
     */
    PAUSED,
    /**
     * Simulation is ended.
     */
    ENDED;
}
