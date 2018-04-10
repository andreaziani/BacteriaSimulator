package controller;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import model.Energy;
import model.PositionImpl;
import model.State;
import model.StateImpl;
import model.bacteria.BacteriaImpl;
import model.bacteria.Species;
import model.food.FoodImpl;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCodeImpl;
import view.model.bacteria.ViewSpecies;
import view.model.food.SimulationViewFood;
import view.model.food.ViewFood;

/**
 * Simplification of a SimpleState object that maintains only useful information
 * for saving and loading simulations and is easily serializable via json.
 */
public class SimpleState {
    private final Map<PositionImpl, SimpleBacteria> bacteriaMap;
    private final Map<PositionImpl, SimulationViewFood> foodMap;

    /**
     * @param state
     *            a State to represent in this object.
     * @param viewFoods
     *            a set of view representations of foods.
     * @param viewSpecies
     *            a set of view representations of species.
     * @throws IllegalArgumentException
     *             if the elements in state do not match with the elements of
     *             viewFood and viewSpecies.
     */
    public SimpleState(final State state, final Set<? extends ViewFood> viewFoods, final Set<ViewSpecies> viewSpecies) {
        // TODO is wildcard necessary?
        try {
            bacteriaMap = state.getBacteriaState().entrySet().stream().collect(Collectors
                    .toMap(x -> (PositionImpl) x.getKey(), x -> new SimpleBacteria(x.getValue(), viewSpecies.stream()
                            .filter(s -> s.getName().equals(x.getValue().getSpecies().getName())).findFirst().get())));
            foodMap = state.getFoodsState().entrySet().stream()
                    .collect(Collectors.toMap(x -> (PositionImpl) x.getKey(),
                            x -> new SimulationViewFood(Optional.of(x.getValue().getName()),
                                    viewFoods.stream().filter(f -> f.getName().equals(x.getValue().getName()))
                                            .findFirst().get().getColor(),
                                    x.getValue().getNutrients().stream().collect(Collectors.toMap(Function.identity(),
                                            n -> x.getValue().getQuantityFromNutrient(n))))));
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return an unmodifiable map of all bacterias and their positions.
     */
    public Map<PositionImpl, SimpleBacteria> getBacteriaMap() {
        return Collections.unmodifiableMap(bacteriaMap);
    }

    /**
     * @return an unmodifiable map of all foods and their positions.
     */
    public Map<PositionImpl, SimulationViewFood> getFoodMap() {
        return Collections.unmodifiableMap(foodMap);
    }

    /**
     * Return a new State constructed from this object and some indicator as to how
     * to construct a Bacteria.
     * 
     * @param speciesMapper
     *            a function to transform a view representation of a species into a
     *            species.
     * @param startingEnergy
     *            a supplier of energy to assign to each bacteria as their starting
     *            amount.
     * @return a new State representing this object in the way it should be
     *         represented in the model.
     */
    public State reconstructState(final Function<ViewSpecies, Species> speciesMapper, final Supplier<Energy> startingEnergy) {
        return new StateImpl(
                getFoodMap().entrySet().stream().collect(Collectors.toMap(x -> x.getKey(),
                        x -> new FoodImpl(x.getValue().getNutrients().stream().collect(
                                Collectors.toMap(Function.identity(), n -> x.getValue().getQuantityFromNutrient(n)))))),
                getBacteriaMap().entrySet().stream()
                        .collect(Collectors.toMap(x -> x.getKey(),
                                x -> new BacteriaImpl(x.getValue().getId(), speciesMapper.apply(x.getValue().getSpecies()),
                                        new GeneticCodeImpl(new GeneImpl(x.getValue().getCode()),
                                                x.getValue().getRadius(), x.getValue().getRadius()),
                                        startingEnergy.get()))));
    }

}
