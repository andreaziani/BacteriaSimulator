package model;

import java.util.Set;

import model.bacteria.Species;
import model.food.Food;

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
     * @param food the new type of food.
     * @throws AlreadyExistingFoodException
     *             if the food already exist.
     */
    void addNewTypeOfFood(Food food);
    /**
     * Get all the existing foods.
     * 
     * @return an unmodifiable copy of the set of existing foods.
     */
    Set<Food> getExistingFoods();

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
     * return the analisys of the species.
     * 
     * @return Analysis of fitness.
     */
    Analysis getAnalisys();

    /**
     * @param species
     *            add a Species to the simulation.
     */
    void addSpecies(Species species);
    /**
     * Get the maximum position in the environment.
     * @return the maximum position.
     */
    Position getMaxPosition();
}
