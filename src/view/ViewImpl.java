package view;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import controller.Controller;
import controller.SimulationState;
import model.Analysis;
import model.action.ActionType;
import model.food.Nutrient;
import model.food.insertionstrategy.position.DistributionStrategy;
import view.model.ViewPosition;
import view.model.ViewPositionImpl;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.bacteria.ViewSpeciesFactory;
import view.model.food.ViewFood;

/**
 * Class that represents the "Controller" of the view package and the access
 * point for the Simulation's Controller.
 *
 */
public class ViewImpl implements View, ViewController {
    private final Controller controller;
    private final ViewSpeciesFactory speciesManager;
    private View userInterface;

    /**
     * Constructor that build a View passing the Controller that allows interactions
     * with the model.
     * 
     * @param controller
     *            the controller that allows interactions with the model.
     */
    public ViewImpl(final Controller controller) {
        this.controller = controller;
        speciesManager = new ViewSpeciesFactory();
    }

    @Override
    public void update(final ViewState state) {
        this.userInterface.update(state);
    }

    @Override
    public void addFood(final ViewFood food, final ViewPosition position) {
        this.controller.addFoodFromView(food, position);

    }

    @Override
    public void showAnalisys(final Analysis analysis) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.controller.addNewTypeOfFood(food);
    }

    @Override
    public List<ViewFood> getFoodsType() {
        return this.controller.getExistingViewFoods().stream().collect(Collectors.toList());
    }

    @Override
    public Map<ActionType, List<String>> getDecisionOptions() {
        return speciesManager.getDecisionOptions();
    }

    @Override
    public List<String> getDecoratorOptions() {
        return speciesManager.getDecoratorOptions();
    }

    @Override
    public void createSpecies(final String name, final Map<ActionType, Integer> decisionOptions,
            final List<Boolean> decorators) {
        controller.addSpecies(speciesManager.createSpecies(name, decisionOptions, decorators));
    }

    @Override
    public List<String> getFoodsName() {
        return this.getFoodsType().stream().map(f -> f.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getNutrients() {
        return Arrays.asList(Nutrient.values()).stream().map(n -> n.toString()).collect(Collectors.toList());
    }

    @Override
    public void setDimension(final Dimension dimension) {
        this.controller.setMaxViewDimension(new ViewPositionImpl(dimension.width, dimension.height));
    }

    @Override
    public void loadSimulation(final File file) throws IOException {
        controller.loadInitialState(file);
    }

    @Override
    public void saveSimulation(final File file) throws IOException {
        controller.saveInitialState(file);
    }

    @Override
    public void loadReplay(final String path) throws IOException {
        controller.loadReplay(path);
    }

    @Override
    public void saveReplay(String path) throws IOException {
        controller.saveReplay(path);
    }

    @Override
    public void saveAnalysis(final File file) throws IOException {
        controller.saveAnalisys(file);
    }

    @Override
    public void startSimulation() {
        this.controller.start();
    }

    @Override
    public boolean isSpeciesEmpty() {
        return this.controller.isSpeciesEmpty();
    }

    @Override
    public void setUserInterface(final View userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void updateExistingFoods() {
        this.userInterface.updateExistingFoods();
    }

    @Override
    public List<String> getAvailableDistributionStrategies() {
        return Arrays.asList(DistributionStrategy.values()).stream().map(n -> n.toString())
                .collect(Collectors.toList());
    }

    @Override
    public void setDistributionStrategy(final DistributionStrategy strategy) {
        this.controller.setDistributionStrategy(strategy);
    }

    @Override
    public void updateSimulationState(final SimulationState state) {
        this.userInterface.updateSimulationState(state);
    }

    @Override
    public Set<ViewSpecies> getSpecies() {
        return this.controller.getSpecies();
    }

    @Override
    public void reset() {
        this.controller.resetSimulation();
    }
}
