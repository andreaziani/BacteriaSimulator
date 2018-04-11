package controller;
/**
 * 
 * All possible state that simulation can have.
 *
 */
public enum SimulationState {
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
    PAUSE,
    /**
     * Replay is running.
     */
    REPLAY,
    /**
     * Simulation is ended.
     */
    END;
}
