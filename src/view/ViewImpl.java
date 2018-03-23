package view;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import controller.Controller;
import model.Analisys;
import model.action.ActionType;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.food.Nutrient;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;

/**
 * Implementation of View.
 *
 */
public class ViewImpl implements View {
    private final Controller controller;
    private ViewState state;
    private final Map<ActionType, List<DecisionMakerOption>> decisionOptionsMap;
    private final List<BehaviorDecoratorOption> decoratorOptionsList;

    /**
     * Constructor that build a View and initializing her observers.
     * 
     * @param controller
     *            controller that allows interactions with the model.
     */
    public ViewImpl(final Controller controller) {
        this.controller = controller;
        decisionOptionsMap = Arrays.asList(ActionType.values()).stream()
                .collect(Collectors.toMap(Function.identity(), a -> Arrays.asList(DecisionMakerOption.values()).stream()
                        .filter(x -> x.getType().equals(a)).collect(Collectors.toList())));
        decoratorOptionsList = Arrays.asList(BehaviorDecoratorOption.values());
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

    @Override
    public Map<ActionType, List<String>> getDecisionOptions() {
        return decisionOptionsMap.entrySet().stream().collect(Collectors.toMap(x -> x.getKey(),
                x -> x.getValue().stream().map(d -> d.toString()).collect(Collectors.toList())));
    }

    @Override
    public List<String> getDecoratorOptions() {
        return decoratorOptionsList.stream().map(x -> x.toString()).collect(Collectors.toList());
    }

    @Override
    public void createSpecies(final String name, final Color color, final Map<ActionType, Integer> decisionOptions,
            final List<Boolean> decorators) {
        controller.addSpecies(new ViewSpecies(name, color,
                decisionOptions.entrySet().stream().map(x -> decisionOptionsMap.get(x.getKey()).get(x.getValue()))
                        .collect(Collectors.toSet()),
                decoratorOptionsList.stream().filter(x -> decorators.get(decoratorOptionsList.indexOf(x)))
                        .collect(Collectors.toList())));
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
