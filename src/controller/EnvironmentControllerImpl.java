package controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import model.Analysis;
import model.Environment;
import model.InteractiveEnvironment;
import model.bacteria.species.SpeciesOptions;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.replay.Replay;
import model.replay.ReplayEnvironment;
import model.simulator.SimulatorEnvironment;
import model.state.InitialState;
import model.state.State;
import utils.Logger;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.food.ViewFood;

/**
 * Implementation of EnvironmentController.
 *
 */
public abstract class EnvironmentControllerImpl implements EnvironmentController {
    private Environment environment;
    private Optional<ViewPosition> maxViewPosition = Optional.empty();
    private Replay replay;
    private SimulationState currentState;
    private SimulationLoop simLoop;

    /**
     * Constructor of EnvironmentController.
     */
    public EnvironmentControllerImpl() {
        initialize(Optional.empty());
    }

    private void initialize(final Optional<InitialState> initialState) {
        if (initialState.isPresent()) {
            this.environment = new SimulatorEnvironment(initialState.get());
        } else {
            this.environment = new SimulatorEnvironment();
        }
        this.simLoop = new SimulationLoop(this, this.environment);
        this.currentState = SimulationState.NOT_READY;
    }

    @Override
    public synchronized void start() {
        this.environment.initialize();
        replay = new Replay(this.environment.getInitialState());
        final Thread mainThread = new Thread(this.simLoop);

        Logger.getInstance().info("Controller", "Application started");
        mainThread.start();
    }

    public synchronized void stop() {
        this.simLoop.stop();
    }

    public synchronized void pause() {
        this.simLoop.pause();
    }

    public synchronized void resume() {
        this.simLoop.resume();
    }

    @Override
    public final void resetSimulation() {
        updateCurrentState(SimulationState.NOT_READY);
        initialize(Optional.empty());
    }

    /**
     * Restore the simulation to a state defined by an InitialState object. Before
     * setting the state, the view will be notified that the state of the simulation
     * is NOT_READY.
     * 
     * @param initialState
     *            the representation of the initial state.
     */
    protected void setInitialState(final InitialState initialState) {
        this.initialize(Optional.of(initialState));
        if (initialState.getExistingFood().isEmpty() || initialState.getSpecies().isEmpty()) {
            this.updateCurrentState(SimulationState.NOT_READY);
        } else if (initialState.hasState()) { // Better to set in PAUSED
            this.start();
        } else {
            this.updateCurrentState(SimulationState.READY);
        }
    }

    /**
     * @return the initial state of the simulation.
     */
    protected InitialState getInitialState() {
        return environment.getInitialState();
    }

    /**
     * @return a replay representing the simulation.
     */
    protected Replay getReplay() {
        return this.replay;
    }

    /**
     * @return the analysis of the simulation.
     */
    public Analysis getAnalysis() {
        return environment.getAnalysis();
    }

    /**
     * method intended for allowing EnvController and Controller to "communicate".
     */
    public abstract void simulationLoop();

    /**
     * @param replay
     *            a replay from which to construct a ReplayEnvironment.
     */
    protected void startReplay(final Replay replay) {
        this.environment = new ReplayEnvironment(replay);
        this.simLoop = new SimulationLoop(this, this.environment);
        start();
    }

    @Override
    public synchronized void addFoodFromView(final ViewFood food, final ViewPosition position) {
        FoodControllerUtils.addFoodFromViewToModel(this.getEnvironmentAsInteractive(), food,
                ConversionsUtil.viewPositionToPosition(position, environment.getMaxPosition(), maxViewPosition.get()));
    }

    @Override
    public synchronized void addNewTypeOfFood(final ViewFood food) {
        FoodControllerUtils.addNewTypeOfFood(this.getEnvironmentAsInteractive(), food);

        if (this.currentState == SimulationState.NOT_READY && !this.isSpeciesEmpty()) {
            this.updateCurrentState(SimulationState.READY);
        }
    }

    @Override
    public synchronized List<ViewFood> getExistingViewFoods() {
        return FoodControllerUtils.getExistingViewFoods(this.environment);
    }

    @Override
    public synchronized ViewState getState() {
        return ConversionsUtil.stateToViewState(this.environment.getState(), this.environment.getMaxPosition(),
                this.maxViewPosition.get(), environment.getInitialState());
    }

    @Override
    public synchronized void addSpecies(final SpeciesOptions species) {
        try {
            if (this.currentState == SimulationState.NOT_READY && !this.getExistingViewFoods().isEmpty()) {
                this.updateCurrentState(SimulationState.READY);
            }
            getEnvironmentAsInteractive().addSpecies(species);
        } catch (IllegalStateException e) {
            throw new InvalidSpeciesExeption();
        }
        if (this.currentState != SimulationState.NOT_READY && this.currentState != SimulationState.READY) {
            throw new SimulationAlreadyStartedExeption();
        }
    }

    @Override
    public synchronized void setMaxViewDimension(final ViewPosition position) {
        this.maxViewPosition = Optional.of(position);
    }

    @Override
    public synchronized boolean isSpeciesEmpty() {
        return this.getInitialState().getSpecies().isEmpty();
    }

    @Override
    public void setDistributionStrategy(final DistributionStrategy strategy) {
        this.getEnvironmentAsInteractive().setFoodDistributionStrategy(strategy);
    }

    /**
     * Update the current state of the simulation.
     * 
     * @param state
     *            the state that the simulation'll assume.
     */
    public void updateCurrentState(final SimulationState state) {
        this.currentState = state;
        if (this.currentState == SimulationState.ENDED) {
            this.replay.setAnalysis(environment.getAnalysis());
        }
    }

    public SimulationState getCurrentState() {
        return this.currentState;
    }

    @Override
    public void addReplayState(final State currentState) {
        this.replay.addState(currentState);
    }

    @Override
    public Set<SpeciesOptions> getSpecies() {
        return this.getInitialState().getSpecies();
    }

    private InteractiveEnvironment getEnvironmentAsInteractive() {
        if (!(this.environment instanceof InteractiveEnvironment)) {
            throw new IllegalStateException();
        }
        return (InteractiveEnvironment) environment;
    }
}
