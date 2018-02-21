package controller;

import view.InitialState;
import view.ViewPosition;
import view.food.ViewFood;

/**Env Controller.
 * 
 * 
 *
 */
public interface EnvironmentController {
    /** Add any food from view.
     * @param food to add.
     * @param position of the food.
     */
    void addFoodFromView(ViewFood food, ViewPosition position);
    /** Start.
     * @param state Initial.
     */
    void start(InitialState state);
}
