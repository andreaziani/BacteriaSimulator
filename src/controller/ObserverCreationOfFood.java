package controller;

import view.food.ViewFood;
/**
 * The Observer interface, observe the creation of food by the user.
 * User can create foods in the simulation.
 *
 */
public interface ObserverCreationOfFood {
    /**
     * A new Food is inserted.
     * @param food to add in the existingfoods.
     */
    void update(ViewFood food);
}
