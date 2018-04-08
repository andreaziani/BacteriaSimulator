package controller;

import java.util.List;
import view.model.ViewPosition;
import view.model.food.ViewFood;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;

/**
 * Interface of the EnvironmentController, it deals with the management of
 * interactions and changes that user applies in the simulation.
 * 
 *
 */
public interface EnvironmentController {
    /**
     * Add a type of food from the view to a specific location.
     * 
     * @param food
     *            the type of Food to add.
     * @param position
     *            the location of the food in the view.
     * @throws PositionAlreadyOccupiedException
     *             if the position is already occupied.
     */
    void addFoodFromView(ViewFood food, ViewPosition position);

    /**
     * Start the simulation.
     */
    void start();

    /**
     * Add a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to be added.
     * @throws AlreadyExistingFoodException
     *             if the food is already existing.
     */
    void addNewTypeOfFood(ViewFood food);

    /**
     * Get all types of already existing food.
     * 
     * @return the list with all types of food.
     */
    List<ViewFood> getExistingViewFoods();

    /**
     * Transforms the State and returns it as ViewState.
     * 
     * @return the last ViewState.
     */
    ViewState getState();

    /**
     * Add a species to the Environment before the simulation is started, the
     * Species must have a unique name.
     * 
     * @param species
     *            the new Species.
     * @throws SimulationAlreadyStartedExeption
     *             if the simulation is already started.
     * @throws InvalidSpeciesExeption
     *             if the given Species cannot be added correctly.
     * @throws AlreadyExistingSpeciesExeption
     *             if a species with that name already exists.
     */
    void addSpecies(ViewSpecies species);

    /**
     * Set the maximum view position.
     * 
     * @param maxDimension
     *            the maximum dimension of the view.
     */
    void setMaxViewDimension(ViewPosition maxDimension);
    /**
     * Return if the simulation is started.
     * @return true if the simulation is started, false in other case.
     */
    boolean isSimulationStarted();
    /**
     * Return if some species is present.
     * @return true if there are no species created, false in other case.
     */
    boolean isSpeciesEmpty();
}
