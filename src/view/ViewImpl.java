package view;

import java.util.Set;

import model.Analisys;
import view.food.ViewFood;
/**
 * Implementation of View.
 * @author Andrea PC
 *
 */
public class ViewImpl implements View {
    private final ViewInteraction interaction = new ViewInteractionImpl();
    private final ObserverExistingFoods observer;
    /**
     * Constructor that build a View and initialize an observer.
     * @param observer to add.
     */
    public ViewImpl(final ObserverExistingFoods observer) {
        this.observer = observer;
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
