package view;

import controller.ObserverInsertionFromView;
import view.food.ViewFood;

/**
 * Interface that allows observing and communicate to all the observers
 * the inseriment of foods by user.
 * (Subject observed from ObserverView).
 *
 */
public interface ViewInsertion {
    /**
     * Notify all the observers the insertion a new food by user.
     * @param food to add.
     * @param position of the food.
     */
    void notifyInsertionOfFood(ViewFood food, ViewPosition position);
    /**
     * Add an observer on the foodmanager.
     * @param observer to add.
     */
    void addObserver(ObserverInsertionFromView observer);
    /**
     * Remove an observer from the foodmanger.
     * @param observer to remove.
     */
    void removeObserver(ObserverInsertionFromView observer);
}
