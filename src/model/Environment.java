package model;

import java.util.List;

import controller.InitialState;
import model.bacteria.species.Species;
import model.bacteria.species.SpeciesOptions;
import model.food.Food;
import model.food.insertionstrategy.position.DistributionStrategy;

/**
 * Environment of the simulation, deals with all the actions in the simulation.
 * 
 *
 */
public interface Environment {
    /**
     * Add a food in a position.
     * 
     * @param food
     *            to insert.
     * @param position
     *            of the food in the environment.
     * @throws PositionAlreadyOccupiedException
     *             if the position is already occupied by another food.
     */
    void addFood(Food food, Position position);

    /**
     * Add a new type of food in the ExistingFoodManager.
     * 
     * @param food
     *            the new type of food.
     * @throws AlreadyExistingFoodException
     *             if the food already exist.
     */
    void addNewTypeOfFood(Food food);

    /**
     * Get all the existing foods.
     * 
     * @return an unmodifiable copy of the list of existing foods.
     */
    List<Food> getExistingFoods();

    /**
     * return the EnvState.
     * 
     * @return EnvState state of env.
     */
    State getState();

    /**
     * update environment.
     */
    void update();

    /**
     * return the analysis of the species.
     * 
     * @return Analysis of fitness.
     */
    Analysis getAnalysis();

    /**
     * @param species
     *            add a Species to the simulation.
     */
    void addSpecies(SpeciesOptions species);

    /**
     * Get the maximum position in the environment.
     * 
     * @return the maximum position.
     */
    Position getMaxPosition();

    /**
     * Set the distribution strategy for foods. The strategy is Uniform distribution
     * by default.
     * 
     * @param strategy
     *            the strategy chosen.
     */
    void setFoodDistributionStrategy(DistributionStrategy strategy);

    /**
     * @return if the simulation is over.
     */
    boolean isSimulationOver();

    /**
     * Initialize the environment inserting elements if none are present.
     * 
     * @throws IllegalStateException
     *             if the initial state of the environment in inconsistent with the
     *             species added to the environment.
     */
    void initialize();

    /**
     * @return the initial state of the environment.
     */
    InitialState getInitialState();
}
