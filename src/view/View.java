package view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import model.Analisys;
import model.action.ActionType;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.food.ViewFood;

/**
 * View.
 * 
 *
 *
 */
public interface View {
    /**
     * Update view state.
     * 
     * @param state
     *            of view.
     */
    void update(ViewState state);

    /**
     * Insert a new food.
     * 
     * @param food
     *            info.
     * @param position
     *            of the food.
     */
    void addFood(ViewFood food, ViewPosition position);

    /**
     * Add a new Food type.
     * 
     * @param food
     *            type of food to add.
     */
    void addNewTypeOfFood(ViewFood food);

    /**
     * 
     * @return all the type of foods created.
     */
    List<ViewFood> getFoodsType();

    /**
     * 
     * @return the list of names of all the type of foods created.
     */
    List<String> getFoodsName();

    /**
     * 
     * @return the existing nutrients.
     */
    List<String> getNutrients();

    /**
     * Load a replay.
     * 
     * @param path
     *            of the Replay file.
     */
    void loadReplay(String path);

    /**
     * Show analisys.
     * 
     * @param analisys
     *            to show.
     */
    void showAnalisys(Analisys analisys);

    /**
     * @return a map associating each ActionType to a list of options for the
     *         DecisionMakers.
     */
    Map<ActionType, List<String>> getDecisionOptions();

    /**
     * @return a map associating each ActionType to a list of options for the
     *         BehaviorDecorators.
     */
    List<String> getDecoratorOptions();

    /**
     * Take a species to create from the options given by the user.
     * 
     * @param name
     *            the name of the Species.
     * @param color
     *            the color of the Species.
     * @param decisionOptions
     *            the decision makers associated with each ActionType.
     * @param decorators
     *            the behavior decorators independent from the action types.
     * @throws SimulationAlreadyStartedExeption
     *             - if the simulation is already started.
     * @throws InvalidSpeciesExeption
     *             - if the given Species cannot be added correctly.
     * @throws AlreadyExistingSpeciesExeption
     *             if a species with that name already exists.
     */
    void createSpecies(String name, Color color, Map<ActionType, Integer> decisionOptions, List<Boolean> decorators);
}
