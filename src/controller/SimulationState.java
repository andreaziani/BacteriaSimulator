package controller;

/**
 * 
 * Class that contains the current simulation condition and mode.
 *
 */
public class SimulationState {
    private SimulationCondition currentCondition;
    private SimulationMode currentMode;

    /**
     * Set the current simulation condition.
     * 
     * @param condition
     *            the current simulation condition.
     */
    public void setSimulationCondition(final SimulationCondition condition) {
        this.currentCondition = condition;
    }

    /**
     * Set current simulation mode.
     * 
     * @param mode
     *            the current simulation mode.
     */
    public void setSimulationMode(final SimulationMode mode) {
        this.currentMode = mode;
    }

    /**
     * Get the current simulation condition.
     * 
     * @return the current simulation condition.
     */
    public SimulationCondition getCurrentCondition() {
        return currentCondition;
    }

    /**
     * Get the current simulation mode.
     * 
     * @return the current simulation mode.
     */
    public SimulationMode getCurrentMode() {
        return currentMode;
    }

}
