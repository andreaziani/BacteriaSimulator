package model;

import model.bacteria.Species;
import model.bacteria.SpeciesManager;
import model.food.ExistingFoodManager;
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
     * Get all the existing foods.
     * 
     * @return the manager that have all the existing foods.
     */
    ExistingFoodManager getExistingFoods();

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
     * @return Analisys of fitness.
     */
    Analisys getAnalisys();

    /**
     * @param species
     *            add a Species to the simulation.
     */
    void addSpecies(Species species);
}
