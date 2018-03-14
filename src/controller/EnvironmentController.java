package controller;

import java.util.Set;

import view.model.InitialState;
import view.model.ViewPosition;
import view.model.food.ViewFood;
import view.model.ViewState;

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
    /**
     * Add a new type of food created by user.
     * @param food to be added int the ExistingFoodManager.
     */
    void addNewTypeOfFood(ViewFood food);
    /**
     * 
     * @return a set that contains all the existing types of food.
     */
    Set<ViewFood> getExistingViewFoods();
    /**
     * Transforms the State and returns it as ViewState.
     * @return the last ViewState.
     */
    ViewState getState();
}
