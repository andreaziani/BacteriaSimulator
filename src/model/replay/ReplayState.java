package model.replay;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.EnergyImpl;
import model.bacteria.BacteriaImpl;
import model.bacteria.species.SpeciesBuilder;
import model.food.FoodImpl;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCodeImpl;
import model.state.PositionImpl;
import model.state.State;
import model.state.StateImpl;
import utils.Pair;

/**
 * Condensed representation of a state for a replay that allow better
 * serialization to a json file.
 */
public final class ReplayState {
    private final Set<Pair<PositionImpl, Pair<Integer, Double>>> bacteriaPositions;
    private final Set<Pair<PositionImpl, Integer>> foodPositions;

    /**
     * Create a new ReplayState.
     * 
     * @param state
     *            a state to be condensed.
     * @param species
     *            a list of all species names in the simulation.
     * @param foods
     *            a list of all food names existed in the simulation.
     */
    public ReplayState(final State state, final List<String> species, final List<String> foods) {
        bacteriaPositions = state.getBacteriaState().entrySet().stream()
                .map(x -> new Pair<>((PositionImpl) x.getKey(),
                        new Pair<>(species.indexOf(x.getValue().getSpecies().getName()), x.getValue().getRadius())))
                .collect(Collectors.toSet());
        foodPositions = state.getFoodsState().entrySet().stream()
                .map(x -> new Pair<>((PositionImpl) x.getKey(),
                        foods.indexOf(
                                x.getValue().getName() != null ? x.getValue().getName() : Replay.UNNAMED_FOOD_STRING)))
                .collect(Collectors.toSet());
    }

    /**
     * Reconstruct the state in this object restoring informations from the
     * informations given by the parameters.
     * 
     * @param species
     *            a list of all species names in the simulation.
     * @param foods
     *            a list of all food names existed in the simulation.
     * @return a new State representing the informations in this object in the
     *         contest given by the parameters.
     */
    public State reconstructState(final List<String> species, final List<String> foods) {
        return new StateImpl(
                foodPositions.stream().collect(Collectors.toMap(x -> x.getFirst(),
                        x -> new FoodImpl(foods.get(x.getSecond()).equals(Replay.UNNAMED_FOOD_STRING) ? Optional.empty()
                                : Optional.of(foods.get(x.getSecond())), Collections.emptyMap()))),
                bacteriaPositions.stream()
                        .collect(Collectors.toMap(x -> x.getFirst(), x -> new BacteriaImpl(x.getSecond().getFirst(),
                                new SpeciesBuilder(species.get(x.getSecond().getFirst())).build(),
                                new GeneticCodeImpl(new GeneImpl(), x.getSecond().getSecond(), 0), EnergyImpl.ZERO))));
    }

    @Override
    public int hashCode() {
        return Objects.hash(bacteriaPositions, foodPositions);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ReplayState other = (ReplayState) obj;
        return this.bacteriaPositions.containsAll(other.bacteriaPositions)
                && other.bacteriaPositions.containsAll(this.bacteriaPositions)
                && this.foodPositions.containsAll(other.foodPositions)
                && other.foodPositions.containsAll(this.foodPositions);
    }
}
