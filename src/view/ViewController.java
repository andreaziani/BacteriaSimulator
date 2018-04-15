package view;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Analysis;
import model.action.ActionType;
import model.food.insertionstrategy.position.DistributionStrategy;
import view.model.ViewPosition;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;

/**
 * Controller of the View components, it is the interface from the user
 * interface to the rest of the application.
 */
public interface ViewController {
    /**
     * Insert a type of food to a specific location.
     * 
     * @param food
     *            the food to insert.
     * @param position
     *            the position of the food.
     * @throws PositionAlreadyOccupiedException
     *             if the position is already occupied from another food.
     */
    void addFood(ViewFood food, ViewPosition position);

    /**
     * Add a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to add.
     * @throws AlreadyExistingFoodException
     *             if the food is already created.
     */
    void addNewTypeOfFood(ViewFood food);

    /**
     * Get all types of already existing food.
     * 
     * @return all the type of foods created.
     */
    List<ViewFood> getFoodsType();

    /**
     * Get all names of all types of already existing food.
     * 
     * @return the list of names of all the type of foods created.
     */
    List<String> getFoodsName();

    /**
     * Get all types of existing nutrients.
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
     * @param analysis
     *            to show.
     */
    void showAnalisys(Analysis analysis);

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
     *             - if the simulation is already started.
     * @throws InvalidSpeciesExeption
     *             - if the given Species cannot be added correctly.
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
     * Try loading a simulation.
     * 
     * @param file
     *            the file to load from.
     * @throws IOException
     *             in case of a problem.
     */
    void loadSimulation(File file) throws IOException;

    /**
     * Save a simulation.
     * 
     * @param file
     *            the file to save into.
     * @throws IOException
     *             in case of a problem.
     */
    void saveSimulation(File file) throws IOException;

    /**
     * Save an Analysis.
     * 
     * @param file
     *            the file to save into.
     * @throws IOException
     *             in case of a problem.
     */
    void saveAnalysis(File file) throws IOException;

    /**
     * Start the simulation.
     */
    void startSimulation();

    /**
     * Return if the user has not yet entered the species.
     * 
     * @return true if the user has not yet entered the species, false in other
     *         case.
     */
    boolean isSpeciesEmpty();

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

    /**
     * Set the distribution strategy for foods.
     * 
     * @param strategy
     *            the strategy chosen.
     */
    void setDistributionStrategy(DistributionStrategy strategy);

    /**
     * @return a set containing all the species currently present in the simulation.
     */
    Set<ViewSpecies> getSpecies();
}
