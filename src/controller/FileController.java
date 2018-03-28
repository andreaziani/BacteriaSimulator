package controller;

import model.Analisys;
import model.State;

/**
 * FileController.
 * 
 * 
 *
 */
public interface FileController {
    /**
     * Load an initial state of a simulation.
     * 
     * @param path
     *            the location of the file.
     */
    void loadInitialState(String path);

    /**
     * Save the initial state of a simulation for rerunning.
     * 
     * @param path
     *            the location of the file.
     * @param initialState
     *            the description of the initial state of the simulation.
     */
    void saveInitialState(String path, State initialState);

    /**
     * Load a replay of a simulation.
     * 
     * @param path
     *            the location of the file.
     */
    void loadReplay(String path);

    /**
     * Save a replay.
     * 
     * @param path
     *            the location of the file.
     * @param replay
     *            a replay of a simulation.
     */
    void saveReplay(String path, Replay replay);

    /**
     * Save the final analisys.
     * 
     * @param path
     *            the location of the file.
     * @param analisys
     *            a textual analisys of the simulation.
     */
    void saveAnalisys(String path, Analisys analisys);
}
