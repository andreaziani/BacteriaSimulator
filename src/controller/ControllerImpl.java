package controller;

import java.util.Set;

import model.Analisys;
import model.Environment;
import model.simulator.SimulatorEnvironmentImpl;
import view.model.InitialState;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.food.ViewFood;
/**
 * Controller implementation. 
 *
 */
public class ControllerImpl implements Controller {
    // probabilmente meglio creare un nuovo env ad ogni "start"
    private final Environment env = new SimulatorEnvironmentImpl();
    private final EnvironmentController envController = new EnvironmentControllerImpl(env);
    private final FileController fileController = new FileControllerImpl();

    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.envController.addFoodFromView(food, position);
    } 

    @Override
    public void start(final InitialState state) {
        this.envController.start(state);
    }

    @Override
    public void loadReplay(final String path) {
        this.fileController.loadReplay(path);
    }

    @Override
    public void saveReplay(final String path, final Replay rep) {
        this.fileController.saveReplay(path, rep);
    }

    @Override
    public void saveAnalisys(final String path, final Analisys analisys) {
        this.fileController.saveAnalisys(path, analisys);
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.envController.addNewTypeOfFood(food);
    }

    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return this.envController.getExistingViewFoods();
    }

    @Override
    public ViewState getState() {
        return this.envController.getState();
    }
}
