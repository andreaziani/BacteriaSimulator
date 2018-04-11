package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.State;

/**
 * Replay of a simulation, contains a list of serializable states that can be
 * used to reconstruct an environment for additional visualization.
 */
public class Replay {
    private final InitialState initialState;
    private final List<SimpleState> stateList;

    /**
     * Create a replay with only the information about the initial state.
     * 
     * @param initialState
     *            the initial state of the simulation.
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
     *            a state to be inserted as the next element of the replay.
     */
    public void addState(final State state) {
        stateList.add(new SimpleState(state, initialState.getExistingFood(), initialState.getSpecies()));
    }

    /**
     * @return the initial state of this replay.
     */
    public InitialState getInitialState() {
        return initialState;
    }
}
