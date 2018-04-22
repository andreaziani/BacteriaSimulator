package view;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import controller.Controller;
import controller.SimulationCondition;
import model.action.ActionType;
import model.food.Nutrient;
import model.food.insertionstrategy.position.DistributionStrategy;
import view.model.ViewPositionImpl;
import view.model.ViewState;
import view.model.bacteria.SpeciesOptionsFactory;
import view.model.food.ViewFood;

/**
 * Class that represents the "Controller" of the view package and the access
 * point for the Simulation's Controller.
 *
 */
public final class ViewImpl implements View, ViewController {
    private final Controller controller;
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
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void update(final ViewState state) {
        this.userInterface.update(state);
    }

    @Override
    public List<ViewFood> getFoodTypes() {
        return this.controller.getExistingViewFoods().stream().collect(Collectors.toList());
    }

    @Override
    public Map<ActionType, List<String>> getDecisionOptions() {
        return SpeciesOptionsFactory.getDecisionOptions();
    }

    @Override
    public List<String> getDecoratorOptions() {
        return SpeciesOptionsFactory.getDecoratorOptions();
    }

    @Override
    public void createSpecies(final String name, final Map<ActionType, Integer> decisionOptions,
            final List<Boolean> decorators) {
        controller.addSpecies(SpeciesOptionsFactory.createSpecies(name, decisionOptions, decorators));
    }

    @Override
    public List<String> getFoodNames() {
        return this.getFoodTypes().stream().map(f -> f.getName()).collect(Collectors.toList());
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
    public void updateSimulationState(final SimulationCondition state) {
        this.userInterface.updateSimulationState(state);
    }
}
