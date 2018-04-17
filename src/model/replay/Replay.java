package model.replay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Analysis;
import model.AnalysisImpl;
import model.state.InitialState;
import model.state.SimpleState;
import model.state.State;

/**
 * Replay of a simulation, contains a list of serializable states that can be
 * used to reconstruct an environment for additional visualization.
 */
public final class Replay {
    private final InitialState initialState;
    private final List<SimpleState> stateList;
    private Optional<AnalysisImpl> analysis;

    /**
     * Create a replay with only the information about the initial state.
     * 
     * @param initialState
     *            the initial state of the simulation.
     * 
     * @throws IllegalArgumentException
     *             if the initial state has not a state yet.
     */
    public Replay(final InitialState initialState) {
        this.initialState = initialState;
        this.stateList = new ArrayList<>();
        try {
            stateList.add(initialState.getState());
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return a list of all states stored in the replay.
     */
    public List<SimpleState> getStateList() {
        return Collections.unmodifiableList(stateList);
    }

    /**
     * @param state
     *            a state to be inserted as the next element of the replay. No check
     *            is made to assert that this state is compatible with the initial
     *            state of this replay.
     */
    public void addState(final State state) {
        stateList.add(new SimpleState(state, initialState.getSpecies()));
    }

    /**
     * @return the analysis of the replay.
     * @throws NoSuchElementException
     *             if the analysis has not been set yet.
     */
    public Analysis getAnalysis() {
        return analysis.get();
    }

    /**
     * Set the analysis of this simulation.
     * 
     * @param analysis
     *            the analysis of the simulation. No check is made to assert that
     *            the analysis is compatible with initialState.
     */
    public void setAnalysis(final Analysis analysis) {
        this.analysis = Optional.of(new AnalysisImpl(analysis.getDescription()));
    }

    /**
     * @return the initial state of this replay.
     */
    public InitialState getInitialState() {
        return initialState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialState, stateList, analysis);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Replay other = (Replay) obj;
        return Objects.equals(this.initialState, other.initialState) && Objects.equals(this.analysis, other.analysis) && this.stateList.equals(other.stateList);
    }
}
