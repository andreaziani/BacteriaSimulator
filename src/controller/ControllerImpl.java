package controller;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Analisys;
import model.SimulatorEnvironmentImpl;
import view.InitialState;
import view.ViewPosition;
import view.food.ViewFood;
/**
 * Controller implementation. 
 *
 */
public class ControllerImpl implements Controller {
    private final FoodController foodController = new FoodControllerImpl(new SimulatorEnvironmentImpl());
    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, position);
    }

    @Override
    public void start(final InitialState state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadReplay(final String path) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveReplay(final String path, final Replay rep) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveAnalisys(final String path, final Analisys analisys) {
        // TODO Auto-generated method stub

    }

}
