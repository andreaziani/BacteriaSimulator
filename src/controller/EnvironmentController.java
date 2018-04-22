package controller;

import java.util.List;
import java.util.Set;

import model.Analysis;
import model.bacteria.species.SpeciesOptions;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.state.State;
import view.model.ViewPosition;
import view.model.food.ViewFood;
import view.model.ViewState;

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
     * @throws IllegalStateException
     *             if the Environment is not interactive
     */
    void addFoodFromView(ViewFood food, ViewPosition position);

    /**
     * Start the simulation.
     */
    void start();

    /**
     * Stop the simulation.
     */
    void stop();

    /**
     * Pause the simulation.
     */
    void pause();

    /**
     * Resume the simulation from pause.
     */
    void resume();

    /**
     * Add a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to be added.
     * @throws AlreadyExistingFoodException
     *             if the food already exist.
     * @throws IllegalStateException
     *             if the Environment is not interactive
     */
    void addNewTypeOfFood(ViewFood food);

    /**
     * Get all types of already existing food.
     * 
     * @return an unmodifiable list with all types of food.
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
     * @throws AlreadyExistingSpeciesExeption
     *             if a species with that name already exists.
     * @throws IllegalStateException
     *             if the Environment is not interactive
     */
    void addSpecies(SpeciesOptions species);

    /**
     * Set the maximum view dimension.
     * 
     * @param maxDimension
     *            the maximum dimension of the view.
     */
    void setMaxViewDimension(ViewPosition maxDimension);

    /**
     * Return if some species is present.
     * 
     * @return true if there are no species created, false in other case.
     */
    boolean isSpeciesEmpty();

    /**
     * Set the distribution strategy for foods. The strategy is Uniform distribution
     * by default.
     * 
     * @param strategy
     *            the strategy chosen.
     * @throws IllegalStateException
     *             if the Environment is not interactive
     */
    void setDistributionStrategy(DistributionStrategy strategy);

    /**
     * @return a set containing all the species currently present in the simulation.
     */
    Set<SpeciesOptions> getSpecies();

    /**
     * Reset all information of the simulation and prepare the controller to create
     * a new one.
     */
    void resetSimulation();

    /**
     * @return analysis of simulation.
     */
    Analysis getAnalysis();

    /**
     * Update simulation condition inside the controller.
     * 
     * @param condition
     *            the current Simulation condition.
     * @param mode
     *            the current simulation mode
     */
    void updateCurrentState(SimulationCondition condition, SimulationMode mode);

    /**
     * Retrieve simulation state from the controller.
     * 
     * @return the current Simulation state.
     */
    SimulationState getCurrentState();

    /**
     * Add a new state updated to the Replay.
     * 
     * @param currentState
     *            the current state.
     */
    void addReplayState(State currentState);

    /**
     * Method used to perform the operations that need to be executed at every
     * iteration of the simulation loop.
     */
    void simulationLoop();
}
