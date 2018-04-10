package controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.Position;
import model.PositionImpl;
import model.State;
import view.model.bacteria.ViewSpecies;
import view.model.food.CreationViewFoodImpl;
import view.model.food.SimulationViewFood;

/**
 * Represents all the information needed for a simulation to start.
 */
public class InitialState {
    private Optional<SimpleState> state;
    private final Set<CreationViewFoodImpl> existingFood;
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
    public InitialState(final double maxX, final double maxY) {
        this.state = Optional.empty();
        existingFood = new HashSet<>();
        species = new HashSet<>();
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Create an InitialState from the size of the simulation and a representation
     * of the State of the simulation.
     * 
     * @param maxX
     *            the maximum size of the x coordinate.
     * @param maxY
     *            the maximum size of the y coordinate.
     * @param state
     *            a serializable representation of the State of the environment.
     */
    public InitialState(final double maxX, final double maxY, final SimpleState state) {
        this.state = Optional.of(state);
        existingFood = new HashSet<>();
        species = new HashSet<>();
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Set the state of the simulation from a state of the environment.
     * 
     * @param state
     *            a State of the environment.
     * @throws IllegalStateException
     *             if there are no species of foods in the initialState
     * @throws IllegalArgumentException
     *             if the state does not corresponds to the foods and species
     *             inserted in this object.
     */
    public void setState(final State state) {
        if (species.isEmpty() || existingFood.isEmpty()) {
            throw new IllegalStateException();
        }
        this.state = Optional.of(new SimpleState(state, existingFood, species));
    }

    /**
     * @param food
     *            a food to be added to the set that contains all the foods created
     *            by the user.
     */
    public void addFood(final CreationViewFoodImpl food) {
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
     * @return the positions of all bacteria in a easily serializable
     *         representation.
     * @throws IllegalStateException
     *             if the state has not been set.
     */
    public Map<PositionImpl, SimpleBacteria> getBacteriaMap() {
        try {
            return this.state.get().getBacteriaMap();
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException();
        }

    }

    /**
     * @return the positions of all foods in a easily serializable representation.
     * @throws IllegalStateException
     *             if the state has not been set.
     */
    public Map<PositionImpl, SimulationViewFood> getFoodMap() {
        try {
            return this.state.get().getFoodMap();
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException();
        }
    }

    /**
     * @return all the foods created by the user.
     */
    public Set<CreationViewFoodImpl> getExistingFood() {
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
