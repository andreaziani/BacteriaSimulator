package model.replay;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import model.Analysis;
import model.EnergyImpl;
import model.bacteria.species.SpeciesBuilder;
import model.food.Food;
import model.state.InitialState;
import model.state.Position;
import model.state.State;

/**
 * implementation of ReplayEnvironment.
 *
 */
public final class ReplayEnvironmentImpl implements ReplayEnvironment {

    private final InitialState initialState;
    private final Iterator<State> states;
    private final Analysis analysis;
    private Optional<State> currentState;

    /**
     * Creates an environment that reproduce an other environment described by a replay.
     * 
     * @param replay a replay of the simulation.
     */
    public ReplayEnvironmentImpl(final Replay replay) {
        this.initialState = replay.getInitialState();
        this.states = replay.getStateList()
                            .stream()
                            .map(x -> x.reconstructState(
                                    s -> new SpeciesBuilder(s.getName()).build(), () -> EnergyImpl.ZERO))
                            .iterator();
        this.analysis = replay.getAnalysis();
    }

    @Override
    public void initialize() {
        update();
    }

    @Override
    public State getState() {
        return currentState.orElseThrow(() -> new IllegalStateException());
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
    public Position getMaxPosition() {
        return initialState.getMaxPosition();
    }

    @Override
    public List<Food> getExistingFoods() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSimulationOver() {
        return !states.hasNext();
    }

    @Override
    public InitialState getInitialState() {
        return this.initialState;
    }
}
