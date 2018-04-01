package controller;

import java.io.IOException;
import java.util.Set;

import model.Analisys;
import model.Environment;
import model.simulator.SimulatorEnvironmentImpl;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;
/**
 * Controller implementation. 
 *
 */
public class ControllerImpl implements Controller {
    // probabilmente meglio creare un nuovo env ad ogni "start"
    private final Environment env = new SimulatorEnvironmentImpl();
    private final FileController fileController = new FileControllerImpl();
    private ViewPosition dimension;
    private final EnvironmentController envController = new EnvironmentControllerImpl(env);

    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.envController.addFoodFromView(food, position);
    } 

    @Override
    public void start() {
        this.envController.setMaxViewDimension(dimension);
        this.envController.start();
    }

    @Override
    public void loadInitialState(final String path) throws IOException {
        this.fileController.loadInitialState(path);
    }

    @Override
    public void saveInitialState(final String path, final InitialState initialState) throws IOException {
        this.fileController.saveInitialState(path, initialState);
    }
    @Override
    public void loadReplay(final String path) {
        this.fileController.loadReplay(path);
    }

    @Override
    public void saveReplay(final String path, final Replay replay) {
        this.fileController.saveReplay(path, replay);
    }

    @Override
    public void saveAnalisys(final String path, final Analisys analisys) throws IOException {
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

    @Override
    public void addSpecies(final ViewSpecies species) {
        this.envController.addSpecies(species);
    }

    @Override
    public void setMaxViewDimension(final ViewPosition maxDimension) {
        this.dimension = maxDimension;
        this.envController.setMaxViewDimension(dimension);
    }

    @Override
    public void startFromInitialState() {
        envController.startFromInitialState();
    }
}
