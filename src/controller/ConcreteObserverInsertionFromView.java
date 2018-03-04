package controller;
import view.ViewPosition;
import view.food.ViewFood;
/**
 * Concrete Observer on user insertions of foods.
 *
 */
public class ConcreteObserverInsertionFromView implements ObserverInsertionFromView {
    private final Controller controller;
    /**
     * Constructor that build the Observer by taking a controller 
     * and then updating it.
     * @param controller on which to act.
     */
    public ConcreteObserverInsertionFromView(final Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update(final ViewFood food, final ViewPosition position) {
        controller.addFoodFromView(food, position);
    }

}
