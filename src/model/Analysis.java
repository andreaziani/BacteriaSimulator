package model;

import model.state.State;

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
     * The name of winner species.
     * @return string of result of winner species.
     */
    String resultWins();

    /**
     * Species with his number of alive or dead bacteria. 
     * @return string of map species-quantity.
     */
    String resultNByS();

    /**
     * Species are died during the simulation.
     * @return string of dead bacteria set.
     */
    String resultDead();

    /**
     * Quantity of mutated bacteria per species.
     * @return string of map species-quantity.
     */
    String resultBactMutated();

    /**
     * Species are alive until the end of the simulation.
     * @return string of survived bacteria set.
     */
    String resultSurvived();

    /**
     * The description of the Analysis.
     * @return a string of results of bacteria.
     */
    String getDescription();

    /**
     * update and reworked version of Bacteria's data.
     */
    void updateAnalysis();
}
