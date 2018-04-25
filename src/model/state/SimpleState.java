package model.state;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import model.Energy;
import model.bacteria.BacteriaImpl;
import model.bacteria.species.Species;
import model.bacteria.species.SpeciesOptions;
import model.food.Food;
import model.food.FoodImpl;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCodeImpl;
import utils.Pair;

/**
 * Simplification of a SimpleState object that maintains only useful information
 * for saving and loading simulations and is easily serializable via json.
 */
public final class SimpleState {
    private final Set<Pair<PositionImpl, SimpleBacteria>> bacterias;
    private final Set<Pair<PositionImpl, FoodImpl>> foods;

    /**
     * @param state
     *            a State to represent in this object.
     * @param speciesOptions
     *            a set of view representations of species.
     * @throws IllegalArgumentException
     *             if the elements in state do not match with the elements of
     *             viewFood and viewSpecies.
     */
    public SimpleState(final State state, final Set<SpeciesOptions> speciesOptions) {
        try {
            bacterias = state.getBacteriaState().entrySet().stream()
                    .map(x -> new Pair<>((PositionImpl) x.getKey(),
                            new SimpleBacteria(x.getValue(), speciesOptions.stream()
                                    .filter(s -> s.getName().equals(x.getValue().getSpecies().getName()))
                                    .findFirst().get())))
                    .collect(Collectors.toSet());
            foods = state.getFoodsState().entrySet().stream()
                    .map(e -> new Pair<>((PositionImpl) e.getKey(), (FoodImpl) e.getValue()))
                    .collect(Collectors.toSet());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("the state do not mach with the information in this SimpleState");
        }
    }

    /**
     * @return an unmodifiable map of all bacterias and their positions.
     */
    public Map<Position, SimpleBacteria> getBacteriaMap() {
        return bacterias.stream().collect(Collectors.toMap(x -> x.getFirst(), x -> x.getSecond()));
    }

    /**
     * @return an unmodifiable map of all foods and their positions.
     */
    public Map<Position, Food> getFoodMap() {
        return foods.stream().collect(Collectors.toMap(x -> x.getFirst(), x -> x.getSecond()));
    }

    /**
     * Return a new State constructed from this object and some indicator as to how
     * to construct a Bacteria.
     * 
     * @param speciesMapper
     *            a function to map the stored data of a species into a species.
     * @param startingEnergy
     *            a supplier of energy to assign to each bacteria as their starting
     *            amount.
     * @return a new State representing this object in the way it should be
     *         represented in the model.
     */
    public State reconstructState(final Function<SpeciesOptions, Species> speciesMapper,
            final Supplier<Energy> startingEnergy) {
        return new StateImpl(getFoodMap(),
                getBacteriaMap().entrySet().stream()
                        .collect(Collectors.toMap(x -> x.getKey(),
                                x -> new BacteriaImpl(x.getValue().getId(),
                                        speciesMapper.apply(x.getValue().getSpecies()),
                                        new GeneticCodeImpl(new GeneImpl(x.getValue().getCode()),
                                                x.getValue().getRadius(), x.getValue().getPerceptionRadius()),
                                        startingEnergy.get()))));
    }

    @Override
    public int hashCode() {
        return Objects.hash(bacterias, foods);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SimpleState other = (SimpleState) obj;
        return this.bacterias.containsAll(other.bacterias) && other.bacterias.containsAll(this.bacterias)
                && this.foods.containsAll(other.foods) && other.foods.containsAll(this.foods);
    }
}
