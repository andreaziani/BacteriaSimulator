package model;

/** Final analysis.
 * Interface of Analysis.
 * 
 *
 */
public interface Analysis {
    /**
     * Add a new state in a list of state.
     * @param state
     *          new state to be added to a list of state.
     */
    void addState(State state);

    /**
     * The description of the Analysis.
     * @return a string of results of bacteria.
     */
    String getDescription();
}
