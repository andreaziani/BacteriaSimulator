package model.replay;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.AbstractEnvironment;
import model.EnergyImpl;
import model.bacteria.species.SpeciesBuilder;
import model.food.Food;
import model.state.State;

/**
 * implementation of ReplayEnvironment.
 *
 */
public final class ReplayEnvironmentImpl extends AbstractEnvironment implements ReplayEnvironment {

    private final Iterator<State> states;
    private Optional<State> currentState;

    /**
     * Creates an environment that reproduce an other environment described by a replay.
     * 
     * @param replay a replay of the simulation.
     */
    public ReplayEnvironmentImpl(final Replay replay) {
        super(replay.getInitialState(), replay.getAnalysis());
        this.states = replay.getStateList()
                            .stream()
                            .map(x -> x.reconstructState(
                                    s -> new SpeciesBuilder(s.getName()).build(), () -> EnergyImpl.ZERO))
                            .iterator();
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
    public List<Food> getExistingFoods() {
        return this.getInitialState().getExistingFood().stream().map(x -> (Food) x).collect(Collectors.toList());
    }

    @Override
    public boolean isSimulationOver() {
        return !states.hasNext();
    }
}
