package controller;

import java.io.IOException;

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
     * Save the initial state of the current simulation for rerunning.
     * 
     * @param path
     *            the location of the file.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveInitialState(String path) throws IOException;

    /**
     * Load a replay of a simulation.
     * 
     * @param path
     *            the location of the file.
     */
    void loadReplay(String path);

    /**
     * Save the replay of the last runned simulation.
     * 
     * @param path
     *            the location of the file.
     */
    void saveReplay(String path);

    /**
     * Save the final analysis of the last simulation runned.
     * 
     * @param path
     *            the location of the file.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveAnalisys(String path) throws IOException;
}
