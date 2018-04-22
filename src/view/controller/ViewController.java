package view.controller;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import controller.Controller;
import model.action.ActionType;
import view.View;
import view.model.food.ViewFood;

/**
 * Controller of the View components, it is the interface from the user
 * interface to the rest of the application.
 */
public interface ViewController {
    /**
     * @return the controller of the application that is associated to this View.
     */
    Controller getController();

    /**
     * Get all names of all types of already existing food.
     * 
     * @return the list of names of all the type of foods created.
     */
    List<String> getFoodNames();

    /**
     * Get all types of existing nutrients.
     * 
     * @return the existing nutrients.
     */
    List<String> getNutrients();

    /**
     * Get all types of already existing food.
     * 
     * @return all the type of foods created.
     */
    List<ViewFood> getFoodTypes();

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
     * @param decisionOptions
     *            the decision makers associated with each ActionType.
     * @param decorators
     *            the behavior decorators independent from the action types.
     * @throws SimulationAlreadyStartedExeption
     *             if the simulation is already started.
     * @throws IllegalStateException
     *             if the Environment is not interactive.
     * @throws AlreadyExistingSpeciesExeption
     *             if a species with that name already exists.
     */
    void createSpecies(String name, Map<ActionType, Integer> decisionOptions, List<Boolean> decorators);

    /**
     * Set the view dimension.
     * 
     * @param dimension
     *            the dimension to set in the view.
     */
    void setDimension(Dimension dimension);

    /**
     * Set the userInterface.
     * 
     * @param userInterface
     *            the user interface.
     */
    void setUserInterface(View userInterface);

    /**
     * Get the available distribution strategies.
     * 
     * @return the list of available strategies.
     */
    List<String> getAvailableDistributionStrategies();
}
