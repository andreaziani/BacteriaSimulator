package controller;

import java.io.File;
import java.io.IOException;
import view.ViewImpl;

/**
 * Controller.
 * 
 *
 */
public interface Controller extends EnvironmentController {
    /**
     * Load an initial state of a simulation. Before loading, the view will be
     * notified that the state of the simulation is NOT_READY.
     * 
     * @param file
     *            the file to save into.
     * @throws IOException
     *             if any problem reading the file occurred.
     */
    void loadInitialState(File file) throws IOException;

    /**
     * Save the initial state of the current simulation for rerunning.
     * 
     * @param file
     *            the file to save into.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveInitialState(File file) throws IOException;

    /**
     * Load a replay of a simulation.
     * 
     * @param path
     *            the path of the file to save into.
     * @throws IOException
     *             if any problem reading the file occurred.
     */
    void loadReplay(String path) throws IOException;

    /**
     * Save the replay of the last runned simulation.
     * 
     * @param path
     *            the path of the file to save into.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveReplay(String path) throws IOException;

    /**
     * Save the final analysis of the last simulation runned.
     * 
     * @param file
     *            the file to save into.
     * @throws IOException
     *             if any problem writing in the file occurred.
     */
    void saveAnalisys(File file) throws IOException;

    /**
     * Set the view of the simulation.
     * 
     * @param view
     *            the view to be linked to the controller
     */
    void setView(ViewImpl view);
}
