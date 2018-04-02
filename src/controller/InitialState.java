package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Position;
import model.PositionImpl;
import view.model.bacteria.ViewBacteriaImpl;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFoodImpl;

/**
 * Represents all the information needed for a simulation to start.
 */
public class InitialState {
    private Map<PositionImpl, ViewBacteriaImpl> bacteriaMap;
    private Map<PositionImpl, ViewFoodImpl> foodMap;
    private final Set<ViewFoodImpl> existingFood;
    private final Set<ViewSpecies> species;
    private final double maxX;
    private final double maxY;

    /**
     * Create an InitialState from the size of the simulation.
     * 
     * @param maxX
     *            the maximum size of the x coordinate.
     * @param maxY
     *            the maximum size of the y coordinate.
     */
    public InitialState(double maxX, double maxY) {
        bacteriaMap = new HashMap<>();
        foodMap = new HashMap<>();
        existingFood = new HashSet<>();
        species = new HashSet<>();
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Set the state of the simulation in the form of maps of positions and View
     * representation of the simulation objects.
     * 
     * @param bacteriaMap
     *            the map of all bacteria.
     * @param foodMap
     *            the map of all foods.
     */
    public void setState(final Map<PositionImpl, ViewBacteriaImpl> bacteriaMap,
            final Map<PositionImpl, ViewFoodImpl> foodMap) {
        this.bacteriaMap = bacteriaMap;
        this.foodMap = foodMap;
    }

    /**
     * @param food
     *            a food to be added to the set that contains all the foods created
     *            by the user.
     */
    public void addFood(final ViewFoodImpl food) {
        existingFood.add(food);
    }

    /**
     * @param species
     *            a species to be added to the set that contains all the species
     *            created by the user.
     */
    public void addSpecies(final ViewSpecies species) {
        this.species.add(species);
    }

    /**
     * @return the positions of all bacteria and their view representation.
     */
    public Map<PositionImpl, ViewBacteriaImpl> getBacteriaMap() {
        return bacteriaMap;
    }

    /**
     * @return the positions of all foods and their view representation.
     */
    public Map<PositionImpl, ViewFoodImpl> getFoodMap() {
        return foodMap;
    }

    /**
     * @return all the foods created by the user.
     */
    public Set<ViewFoodImpl> getExistingFood() {
        return existingFood;
    }

    /**
     * @return all the species created by the users.
     */
    public Set<ViewSpecies> getSpecies() {
        return species;
    }

    /**
     * @return a Position representing the opposite edge of the (0, 0) in the
     *         environment.
     */
    public Position getMaxPosition() {
        return new PositionImpl(maxX, maxY);
    }
}
