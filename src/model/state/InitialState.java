package model.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import model.Energy;
import model.bacteria.species.Species;
import model.bacteria.species.SpeciesOptions;
import model.food.Food;
import model.food.FoodImpl;

/**
 * Represents all the information needed for a simulation to start.
 */
public final class InitialState {
    private Optional<SimpleState> state;
    private final List<FoodImpl> existingFood;
    private final Set<SpeciesOptions> species;
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
        existingFood = new ArrayList<>();
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
        existingFood = new ArrayList<>();
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
            throw new IllegalStateException("This InitialState is not ready to add a state");
        }
        this.state = Optional.of(new SimpleState(state, species));
    }

    /**
     * @param food
     *            a food to be added to the set that contains all the foods created
     *            by the user.
     */
    public void addFood(final Food food) {
        existingFood.add((FoodImpl) food);
    }

    /**
     * @param species
     *            a species to be added to the set that contains all the species
     *            created by the user.
     */
    public void addSpecies(final SpeciesOptions species) {
        this.species.add(species);
    }

    /**
     * @return the positions of all bacteria in a easily serializable
     *         representation.
     * @throws IllegalStateException
     *             if the state has not been set.
     */
    public Map<Position, SimpleBacteria> getBacteriaMap() {
        return getStateOrIllegalState().getBacteriaMap();
    }

    /**
     * @return the positions of all foods in a easily serializable representation.
     * @throws IllegalStateException
     *             if the state has not been set.
     */
    public Map<Position, Food> getFoodMap() {
        return getStateOrIllegalState().getFoodMap();
    }

    /**
     * @return all the foods created by the user.
     */
    public List<? extends Food> getExistingFood() {
        return existingFood;
    }

    /**
     * @return all the species created by the users.
     */
    public Set<SpeciesOptions> getSpecies() {
        return species;
    }

    /**
     * @return a Position representing the opposite edge of the (0, 0) in the
     *         environment.
     */
    public Position getMaxPosition() {
        return new PositionImpl(maxX, maxY);
    }

    /**
     * @return the state of the simulation.
     * @throws IllegalStateException
     *             if the state has not been set.
     */
    public SimpleState getState() {
        return getStateOrIllegalState();
    }

    /**
     * Return a new State constructed from the contained initial state and some
     * indicator as to how to construct a Bacteria.
     * 
     * @param speciesMapper
     *            a function to transform a view representation of a species into a
     *            species.
     * @param startingEnergy
     *            a supplier of energy to assign to each bacteria as their starting
     *            amount.
     * @return a new State representing the initial state in the way it should be
     *         represented in the model.
     * @throws IllegalStateException
     *             if the state has not been set.
     */
    public State reconstructState(final Function<SpeciesOptions, Species> speciesMapper,
            final Supplier<Energy> startingEnergy) {
        return getStateOrIllegalState().reconstructState(speciesMapper, startingEnergy);
    }

    /**
     * @return a boolean indicating whether there is already a state in the initial
     *         state.
     */
    public boolean hasState() {
        return this.state.isPresent();
    }

    private SimpleState getStateOrIllegalState() {
        return this.state.orElseThrow(() -> new IllegalStateException("The state has not been set yet"));
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, existingFood, species, maxX, maxY);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final InitialState other = (InitialState) obj;
        return Objects.equals(this.maxX, other.maxX) && Objects.equals(this.maxY, other.maxY)
                && Objects.equals(this.state, other.state) && this.existingFood.containsAll(other.existingFood)
                && other.existingFood.containsAll(this.existingFood) && this.species.containsAll(other.species)
                && other.species.containsAll(this.species);
    }
}
