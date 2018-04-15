package controller;

import java.io.IOException;

import model.Analysis;

/**
 * FileController.
 * 
 * 
 *
 */
public interface FileController {
    /**
     * Only accepted extension for initial state files.
     */
    String SIMULATION_EXTENTION = "bacsim";
    /**
     * Only accepted extension for replay files.
     */
    String REPLAY_EXTENTION = "bacrep";

    /**
     * Load an initial state of a simulation.
     * 
     * @param path
     *            the path of the file to load.
     * @return the initial state loaded.
     * @throws IOException
     *             if any problem reading the file occurred.
     * @throws IllegalExtensionExeption
     *             if the extension of the file was not valid.
     * @throws FileFormatException
     *             if the extension of the file was not valid.
     */
    InitialState loadInitialState(String path) throws IOException;

    /**
     * Save the initial state of a simulation for rerunning.
     * 
     * @param path
     *            the path of the file to save into.
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
     *            the path of the file to load.
     * @return the replay loaded.
     * @throws IOException
     *             if any problem reading the file occurred.
     * @throws IllegalExtensionExeption
     *             if the extension of the file was not valid.
     * @throws FileFormatException
     *             if the extension of the file was not valid.
     */
    Replay loadReplay(String path) throws IOException;

    /**
     * Save a replay.
     * 
     * @param path
     *            the path of the file to save into.
     * @param replay
     *            a replay of a simulation.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveReplay(String path, Replay replay) throws IOException;

    /**
     * Save the final analisys.
     * 
     * @param path
     *            the path of the file to save into.
     * @param analysis
     *            a textual analisys of the simulation.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveAnalysis(String path, Analysis analysis) throws IOException;
}
