package view;

import controller.ObserverCreationOfFood;
import controller.ObserverInsertionFromView;
import view.food.ViewFood;

/**
 * Interface that allows observing and communicate to all the observers
 * the inseriment of foods by user.
 * (Subject observed from ObserverView).
 *
 */
public interface ViewInteraction {
    /**
     * Notify all the observers the insertion a new food by user.
     * @param food to add.
     * @param position of the food.
     */
    void notifyInsertionOfFood(ViewFood food, ViewPosition position);
    /**
     * Notify all the observers the creation of a new food.
     * @param food created.
     */
    void notifyCreationOfFood(ViewFood food);
    /**
     * Add an observer on the foodmanager.
     * @param observer to add.
     */
    void addInsertionObserver(ObserverInsertionFromView observer);
    /**
     * Add an observer on the creation of food.
     * @param observer to add.
     */
    void addCreationObserver(ObserverCreationOfFood observer);
    /**
     * Remove an observer for insertion.
     * @param observer to remove.
     */
    void removeInsertionObserver(ObserverInsertionFromView observer);
    /**
     * Remove an observer for creation.
     * @param observer to remove.
     */
    void removeCreationObserver(ObserverCreationOfFood observer);
}
