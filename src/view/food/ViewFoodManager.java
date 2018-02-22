package view.food;

import controller.ObserverInsertionFromView;
import view.ViewPosition;

/**
 * Manager for ViewFoods.
 * Subject observed from ObserverView.
 *
 */
public interface ViewFoodManager {
    /**
     * insert a new food by user.
     * @param food to add.
     * @param position of the food.
     */
    void insertFood(ViewFood food, ViewPosition position);
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
