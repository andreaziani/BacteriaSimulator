package controller;

import view.ObserverExistingFoods;

/** Controller.
 * 
 * 
 *
 */
public interface Controller extends EnvironmentController, FileController {
    /**
     * Add an observer that watch the set of ExistingFoods.
     * @param obs 
     */
    void addObserverExisistingFoods(ObserverExistingFoods obs);
}
