package controller;

import model.Analisys;

/** FileController.
 * 
 * 
 *
 */
public interface FileController {
    /** Load a simulation.
     * 
     * @param path of the file.
     */
    void loadReplay(String path);
   /** Save the replay.
    * 
    * @param path of the file.
    * @param rep to save.
    */
    void saveReplay(String path, Replay rep);
    /** Save the final analisys.
     * 
     * @param path of the file.
     * @param analisys of the simulation.
     */
    void saveAnalisys(String path, Analisys analisys);
}
