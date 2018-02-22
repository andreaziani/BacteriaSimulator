package controller;

import controller.food.FoodController;
import view.ViewPosition;
import view.food.ViewFood;
/**
 * Concrete Observer on user insertions of foods.
 *
 */
public class ConcreteObserverInsertionFromView implements ObserverInsertionFromView {
    private final FoodController controller;
    /**
     * Constructor that build the Observer by taking a controller 
     * and then updating it.
     * @param controller on which to act.
     */
    public ConcreteObserverInsertionFromView(final FoodController controller) {
        this.controller = controller;
    }

    @Override
    public void update(final ViewFood food, final ViewPosition position) {
        controller.addFoodFromViewToModel(food, position);
    }

}
