package view;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.Controller;
import model.Analisys;
import model.action.ActionType;
import model.food.Nutrient;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpeciesFactory;
import view.model.food.ViewFood;

/**
 * Class that represents the "Controller" of the view package and the access
 * point for the Simulation's Controller.
 *
 */
public class ViewImpl implements View {
    private final Controller controller;
    private ViewState state;
    private final ViewSpeciesFactory speciesManager;

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
    public void createSpecies(final String name, final Color color, final Map<ActionType, Integer> decisionOptions,
            final List<Boolean> decorators) {
        controller.addSpecies(speciesManager.createSpecies(name, color, decisionOptions, decorators));
    }

    @Override
    public List<String> getFoodsName() {
        return this.getFoodsType().stream().map(f -> f.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getNutrients() {
        return Arrays.asList(Nutrient.values()).stream().map(n -> n.toString()).collect(Collectors.toList());
    }

}
