package controller;

import view.food.ViewFood;
/**
 * Concrete Observer on user's creation of food.
 *
 *
 */
public class ConcreteObserverCreationOfFood implements ObserverCreationOfFood {
    private final Controller controller;
    /**
     * Constructor that build the Observer by taking a controller 
     * and then updating it.
     * @param controller on which to act.
     */
    public ConcreteObserverCreationOfFood(final Controller controller) {
        this.controller = controller;
    }
    @Override
    public void update(final ViewFood food) {
        controller.addNewFood(food);
    }

}
