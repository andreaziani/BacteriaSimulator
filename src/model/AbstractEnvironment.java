package model;

import model.state.InitialState;
import model.state.Position;

/**
 * Implementation of Environment that leave as unspecified how to initialize the
 * simulation, how an Environment evolves over time, when the simulation ends
 * and how to get the list of existing foods.
 */
public abstract class AbstractEnvironment implements Environment {
    private final InitialState initialState;
    private final Analysis analysis;

    /**
     * Create an AbstractEnvironment from a previously initialized initial state and
     * an analysis.
     * 
     * @param initialState
     *            the initial state of this environment.
     * @param analysis
     *            the analysis of this simulation.
     */
    public AbstractEnvironment(final InitialState initialState, final Analysis analysis) {
        this.initialState = initialState;
        this.analysis = analysis;
    }

    @Override
    public final Analysis getAnalysis() {
        return this.analysis;
    }

    @Override
    public final Position getMaxPosition() {
        return this.initialState.getMaxPosition();
    }

    @Override
    public final InitialState getInitialState() {
        return this.initialState;
    }

}
