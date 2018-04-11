package model.replay;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import controller.InitialState;
import model.Analysis;
import model.Position;
import model.State;
import model.bacteria.Species;
import model.food.Food;
import model.food.insertionstrategy.position.DistributionStrategy;

/**
 * implementation of ReplayEnvironment.
 *
 */
public final class ReplayEnvironmentImpl implements ReplayEnvironment {

    private InitialState initialState;
    private final Iterator<State> states;
    private final Analysis analysis;
    private Optional<State> currentState;

    /**
     * Creates an environment that returns every states specified by the iterator.
     * 
     * @param initialState
     *            the initial state of the simulation.
     * @param states
     *            an iterator of states representing the states of the simulation to
     *            replay.
     * @param analysis
     *            the analysis of the simulation.
     */
    public ReplayEnvironmentImpl(final InitialState initialState, final Iterator<State> states,
            final Analysis analysis) {
        this.initialState = initialState;
        this.states = states;
        this.analysis = analysis;
        update();
    }

    @Override
    public void addFood(final Food food, final Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public State getState() {
        return currentState.orElseThrow(() -> new IllegalStateException());
    }

    @Override
    public void init(final Optional<InitialState> initialState) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update() {
        if (states.hasNext()) {
            currentState = Optional.of(states.next());
        } else {
            currentState = Optional.empty();
        }
    }

    @Override
    public Analysis getAnalysis() {
        return this.analysis;
    }

    @Override
    public void addSpecies(final Species species) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Position getMaxPosition() {
        return initialState.getMaxPosition();
    }

    @Override
    public void addNewTypeOfFood(final Food food) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Food> getExistingFoods() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFoodDistributionStrategy(final DistributionStrategy strategy) {
        throw new UnsupportedOperationException();
    }

}
