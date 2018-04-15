package controller;

import java.io.File;
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
     * Load an initial state of a simulation.
     * 
     * @param file
     *            the file to save into.
     * @return the initial state loaded.
     * @throws IOException
     *             if any problem reading the file occurred.
     */
    InitialState loadInitialState(File file) throws IOException;

    /**
     * Save the initial state of a simulation for rerunning.
     * 
     * @param file
     *            the file to save into.
     * @param initialState
     *            the description of the initial state of the simulation.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveInitialState(File file, InitialState initialState) throws IOException;

    /**
     * Load a replay of a simulation.
     * 
     * @param path
     *            the path of the file to save into.
     * @return the replay loaded.
     * @throws IOException
     *             if any problem reading the file occurred.
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
     * @param file
     *            the file to save into.
     * @param analysis
     *            a textual analisys of the simulation.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveAnalysis(File file, Analysis analysis) throws IOException;
}
