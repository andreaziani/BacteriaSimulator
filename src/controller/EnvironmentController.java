package controller;

import java.util.Set;

import utils.exceptions.AlreadyExistingSpeciesExeption;
import view.model.ViewPosition;
import view.model.food.ViewFood;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;

/**
 * Env Controller.
 * 
 * 
 *
 */
public interface EnvironmentController {
    /**
     * Add any food from view.
     * 
     * @param food
     *            to add.
     * @param position
     *            of the food.
     */
    void addFoodFromView(ViewFood food, ViewPosition position);

    /**
     * Start the simulation.
     */
    void start();

    /**
     * Add a new type of food created by user.
     * 
     * @param food
     *            to be added int the ExistingFoodManager.
     */
    void addNewTypeOfFood(ViewFood food);

    /**
     * 
     * @return a set that contains all the existing types of food.
     */
    Set<ViewFood> getExistingViewFoods();

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
}
