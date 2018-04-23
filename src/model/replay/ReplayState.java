package model.replay;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.EnergyImpl;
import model.bacteria.BacteriaImpl;
import model.bacteria.species.Species;
import model.food.Food;
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
public class ReplayState {
    private final Set<Pair<PositionImpl, Pair<Integer, Double>>> bacteriaPositions;
    private final Set<Pair<PositionImpl, Integer>> foodPositions;

    /**
     * Create a new ReplayState.
     * 
     * @param state
     *            a state to be condensed.
     * @param species
     *            a list of all species in the simulation.
     * @param foods
     *            a list of all foods existed in the simulation.
     */
    public ReplayState(final State state, final List<Species> species, final List<Food> foods) {
        bacteriaPositions = state.getBacteriaState().entrySet().stream()
                .map(x -> new Pair<>((PositionImpl) x.getKey(),
                        new Pair<>(species.indexOf(x.getValue().getSpecies()), x.getValue().getRadius())))
                .collect(Collectors.toSet());
        foodPositions = state.getFoodsState().entrySet().stream()
                .map(x -> new Pair<>((PositionImpl) x.getKey(), foods.indexOf(x.getValue())))
                .collect(Collectors.toSet());
    }

    /**
     * Reconstruct the state in this object restoring informations from the
     * informations given by the parameters.
     * 
     * @param species
     *            a list of all species in the simulation.
     * @param foods
     *            a list of all foods existed in the simulation.
     * @return a new State representing the informations in this object in the
     *         contest given by the parameters.
     */
    public State reconstructState(final List<Species> species, final List<Food> foods) {
        return new StateImpl(
                foodPositions.stream().collect(Collectors.toMap(x -> x.getFirst(), x -> foods.get(x.getSecond()))),
                bacteriaPositions.stream()
                        .collect(Collectors.toMap(x -> x.getFirst(),
                                x -> new BacteriaImpl(x.getSecond().getFirst(), species.get(x.getSecond().getFirst()),
                                        new GeneticCodeImpl(new GeneImpl(), x.getSecond().getSecond(), 0),
                                        EnergyImpl.ZERO))));
    }
}
