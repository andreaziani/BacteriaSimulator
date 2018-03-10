package view;

import java.util.Set;

import controller.ObserverCreationOfFood;
import controller.ObserverInsertionFromView;
import model.Analisys;
import view.food.ViewFood;
/**
 * Implementation of View.
 *
 */
public class ViewImpl implements View {
    private final ViewInteraction interaction = new ViewInteractionImpl();
    private final ObserverExistingFoods observer;
/**
 * Constructor that build a View and initializing her observers.
 * @param observer that watch the set of existing foods.
 * @param observerCreation that watch creation of food.
 * @param observerInsertion that watch insertion of foods.
 */
    public ViewImpl(final ObserverExistingFoods observer, final ObserverCreationOfFood observerCreation, final ObserverInsertionFromView observerInsertion) {
        this.observer = observer;
        this.interaction.addCreationObserver(observerCreation);
        this.interaction.addInsertionObserver(observerInsertion);
    }
    @Override
    public void update(final ViewState state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addFood(final ViewFood food, final ViewPosition position) {
        this.interaction.notifyInsertionOfFood(food, position);

    }

    @Override
    public void loadReplay(final String path) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showAnalisys(final Analisys analisys) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.interaction.notifyCreationOfFood(food);
    }

    @Override
    public Set<ViewFood> getFoodsType() {
        return this.observer.getTypeOfFoods();
    }

}
