package model.replay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.Analysis;
import model.bacteria.species.Species;
import model.bacteria.species.SpeciesOptions;
import model.state.InitialState;
import model.state.State;

/**
 * Replay of a simulation, contains a list of serializable states that can be
 * used to reconstruct an environment for additional visualization.
 */
public final class Replay {
    /**
     * Representation of unnamed food to write in files.
     */
    public static final String UNNAMED_FOOD_STRING = "";

    private final InitialState initialState;
    private final List<String> species;
    private final List<String> foods;
    private final List<ReplayState> stateList;
    private Optional<ReplayAnalysis> analysis;

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
        species = initialState.getSpecies().stream().map(x -> x.getName()).collect(Collectors.toList());
        foods = new ArrayList<>();
        foods.add(UNNAMED_FOOD_STRING);
        initialState.getExistingFood().stream().map(x -> x.getName()).forEach(foods::add);
    }

    /**
     * @param speciesMapper
     *            a function to map the stored data of a species into a species.
     * @return a list of all states stored in the replay.
     */
    public Iterator<State> getStateIterator(final Function<SpeciesOptions, Species> speciesMapper) {
        return getStateList().stream().map(x -> x.reconstructState(species, foods)).iterator();
    }

    /**
     * @return a list of all states stored in the replay.
     */
    public List<ReplayState> getStateList() {
        return Collections.unmodifiableList(stateList);
    }

    /**
     * @param state
     *            a state to be inserted as the next element of the replay. No check
     *            is made to assert that this state is compatible with the initial
     *            state of this replay.
     */
    public void addState(final State state) {
        stateList.add(new ReplayState(state, species, foods));
    }

    /**
     * @param state
     *            a simple state to be inserted as the next element of the replay.
     *            No check is made to assert that this state is compatible with the
     *            initial state of this replay. This method is used by a
     *            deserializer.
     */
    public void addReplayState(final ReplayState state) {
        stateList.add(state);
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
        this.analysis = Optional.of(new ReplayAnalysis(analysis));
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
        return Objects.equals(this.initialState, other.initialState)
                && ((this.analysis.isPresent() && other.analysis.isPresent())
                        || (!this.analysis.isPresent() && !other.analysis.isPresent()))
                && Objects.equals(this.analysis.get().getDescription(), other.analysis.get().getDescription())
                && this.stateList.equals(other.stateList);
    }
}
