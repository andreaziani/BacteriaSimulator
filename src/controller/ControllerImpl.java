package controller;

//import controller.food.FoodController;
//import controller.food.FoodControllerImpl;
import model.Analisys;
import model.Environment;
import model.SimulatorEnvironmentImpl;
import view.InitialState;
import view.ViewPosition;
import view.food.ViewFood;

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

}
