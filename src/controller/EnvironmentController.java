package controller;

import view.InitialState;
import view.ViewFood;

/**Env Controller.
 * 
 * 
 *
 */
public interface EnvironmentController {
    /** Add any food.
     * @param food to add.
     */
    void addFood(ViewFood food);
    /** Start.
     * @param state Initial.
     */
    void start(InitialState state);
}
