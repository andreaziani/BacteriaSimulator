package controller;

import java.io.IOException;

import model.Analisys;

/**
 * Controller.
 * 
 *
 */
public interface Controller extends EnvironmentController {
    /**
     * Load an initial state of a simulation.
     * 
     * @param path
     *            the location of the file.
     * @throws IOException
     *             if any problem reading the file occurred.
     */
    void loadInitialState(String path) throws IOException;

    /**
     * Save the initial state of a simulation for rerunning.
     * 
     * @param path
     *            the location of the file.
     * @param initialState
     *            the description of the initial state of the simulation.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveInitialState(String path, InitialState initialState) throws IOException;

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
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveAnalisys(String path, Analisys analisys) throws IOException;
}
