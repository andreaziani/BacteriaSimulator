package view;

import java.util.Set;

import controller.Controller;
import model.Analisys;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.food.ViewFood;
/**
 * Implementation of View.
 *
 */
public class ViewImpl implements View {
    private final Controller controller;
    private ViewState state;
/**
 * Constructor that build a View and initializing her observers.
 * @param controller controller that allows interactions with the model. 
 */
    public ViewImpl(final Controller controller) {
        this.controller = controller;
    }
    @Override
    public void update(final ViewState state) {
        this.state = state;
    }

    @Override
    public void addFood(final ViewFood food, final ViewPosition position) {
        this.controller.addFoodFromView(food, position);

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
        this.controller.addNewTypeOfFood(food);
    }

    @Override
    public Set<ViewFood> getFoodsType() {
        return this.controller.getExistingViewFoods();
    }

}
