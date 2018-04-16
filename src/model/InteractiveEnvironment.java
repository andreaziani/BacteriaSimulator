package model;

import model.bacteria.species.SpeciesOptions;
import model.food.Food;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.state.Position;

/**
 * Represents an Environment that can be influenced by adding Species and Foods.
 */
public interface InteractiveEnvironment extends Environment {

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
     * @param species
     *            add a Species to the simulation.
     */
    void addSpecies(SpeciesOptions species);

    /**
     * Set the distribution strategy for foods. The strategy is Uniform distribution
     * by default.
     * 
     * @param strategy
     *            the strategy chosen.
     */
    void setFoodDistributionStrategy(DistributionStrategy strategy);
}
