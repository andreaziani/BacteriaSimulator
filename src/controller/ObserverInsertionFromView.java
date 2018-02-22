package controller;

import view.ViewPosition;
import view.food.ViewFood;

/**
 * The Observer interface, observe the insertion by the user.
 * User can insert foods in the simulation.
 *
 */
public interface ObserverInsertionFromView {
    /**
     * Updated every time a food is added from the view.
     * @param food added in View.
     * @param position of the food.
     */
    void update(ViewFood food, ViewPosition position);
}
