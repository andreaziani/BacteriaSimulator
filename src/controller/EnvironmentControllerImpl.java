package controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import controller.util.ConversionsUtil;
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
    private final SimulationState currentState = new SimulationState();
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
        this.currentState.setSimulationCondition(SimulationCondition.NOT_READY);
        this.currentState.setSimulationMode(SimulationMode.INTERACTIVE);
        this.simLoop = new SimulationLoop(this, this.environment);
    }

    @Override
    public final synchronized void start() {
        this.environment.initialize();
        replay = new Replay(this.environment.getInitialState());
        replay.addState(this.environment.getState());
        final Thread mainThread = new Thread(this.simLoop);

        Logger.getInstance().info("Controller", "Application started");
        mainThread.start();
    }

    @Override
    public final synchronized void stop() {
        this.simLoop.stop();
    }

    @Override
    public final synchronized void pause() {
        this.simLoop.pause();
    }

    @Override
    public final synchronized void resume() {
        this.simLoop.resume();
    }

    @Override
    public final synchronized void resetSimulation() {
        updateCurrentState(SimulationCondition.NOT_READY, SimulationMode.INTERACTIVE);
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
    protected synchronized void setInitialState(final InitialState initialState) {
        this.initialize(Optional.of(initialState));
        if (initialState.getExistingFood().isEmpty() || initialState.getSpecies().isEmpty()) {
            this.updateCurrentState(SimulationCondition.NOT_READY, SimulationMode.INTERACTIVE);
        } else if (initialState.hasState()) {
            this.pause();
            this.start();
        } else {
            this.updateCurrentState(SimulationCondition.READY, SimulationMode.INTERACTIVE);
        }
    }

    /**
     * @return the initial state of the simulation.
     */
    protected synchronized InitialState getInitialState() {
        return environment.getInitialState();
    }

    /**
     * @return a replay representing the simulation.
     */
    protected synchronized Replay getReplay() {
        return this.replay;
    }

    /**
     * @return the analysis of the simulation.
     */
    public synchronized Analysis getAnalysis() {
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
    protected synchronized void startReplay(final Replay replay) {
        this.environment = new ReplayEnvironment(replay);
        this.currentState.setSimulationMode(SimulationMode.REPLAY);
        this.currentState.setSimulationCondition(SimulationCondition.PAUSED);
        this.simLoop = new SimulationLoop(this, this.environment);
        this.pause();
        this.start();
    }

    /**
     * Add a type of food from the view to a specific location.
     * 
     * @param food
     *            the type of Food to add.
     * @param position
     *            the location of the food in the view.
     * @throws PositionAlreadyOccupiedException
     *             if the position is already occupied.
     */
    @Override
    public synchronized void addFoodFromView(final ViewFood food, final ViewPosition position) {
        FoodControllerUtils.addFoodFromViewToModel(this.getEnvironmentAsInteractive(), food,
                ConversionsUtil.viewPositionToPosition(position, environment.getMaxPosition(), maxViewPosition.get()));
    }

    /**
     * Add a new type of food to the types of foods that already exist.
     * 
     * @param food
     *            the new type of food to be added.
     * @throws AlreadyExistingFoodException
     *             if the food already exist.
     */
    @Override
    public synchronized void addNewTypeOfFood(final ViewFood food) {
        isSimulationStarted();
        FoodControllerUtils.addNewTypeOfFood(this.getEnvironmentAsInteractive(), food);
        canSimulationBeReady();
    }

    /**
     * Get all types of already existing food.
     * 
     * @return an unmodifiable list with all types of food.
     */
    @Override
    public synchronized List<ViewFood> getExistingViewFoods() {
        return FoodControllerUtils.getExistingViewFoods(this.environment);
    }

    /**
     * Transforms the State and returns it as ViewState.
     * 
     * @return the last ViewState.
     */
    @Override
    public synchronized ViewState getState() {
        return ConversionsUtil.stateToViewState(this.environment.getState(), this.environment.getMaxPosition(),
                this.maxViewPosition.get(), environment.getInitialState());
    }

    @Override
    public final synchronized void addSpecies(final SpeciesOptions species) {
        isSimulationStarted();
        getEnvironmentAsInteractive().addSpecies(species);
        canSimulationBeReady();
    }

    private void canSimulationBeReady() {
        if (this.currentState.getCurrentCondition() == SimulationCondition.NOT_READY
                && (!this.getExistingViewFoods().isEmpty() && !this.isSpeciesEmpty())) {
            this.updateCurrentState(SimulationCondition.READY, SimulationMode.INTERACTIVE);
        }
    }

    private void isSimulationStarted() {
        if (this.currentState.getCurrentCondition() != SimulationCondition.NOT_READY
                && this.currentState.getCurrentCondition() != SimulationCondition.READY) {
            throw new SimulationAlreadyStartedExeption();
        }
    }

    @Override
    public final synchronized void setMaxViewDimension(final ViewPosition position) {
        this.maxViewPosition = Optional.of(position);
    }

    @Override
    public final synchronized boolean isSpeciesEmpty() {
        return this.getInitialState().getSpecies().isEmpty();
    }

    @Override
    public final synchronized void setDistributionStrategy(final DistributionStrategy strategy) {
        this.getEnvironmentAsInteractive().setFoodDistributionStrategy(strategy);
    }

    /**
     * Update simulation condition inside the controller.
     * 
     * @param condition
     *            the current Simulation condition.
     */
    @Override
    public synchronized void updateCurrentState(final SimulationCondition condition, final SimulationMode mode) {
        this.currentState.setSimulationCondition(condition);
        this.currentState.setSimulationMode(mode);
        if (this.currentState.getCurrentCondition() == SimulationCondition.ENDED) {
            this.replay.setAnalysis(environment.getAnalysis());
        }
    }

    @Override
    public final synchronized SimulationState getCurrentState() {
        return this.currentState;
    }

    @Override
    public final synchronized void addReplayState(final State currentState) {
        this.replay.addState(currentState);
    }

    @Override
    public final synchronized Set<SpeciesOptions> getSpecies() {
        return this.getInitialState().getSpecies();
    }

    private InteractiveEnvironment getEnvironmentAsInteractive() {
        if (!(this.environment instanceof InteractiveEnvironment)) {
            throw new IllegalStateException("The environment of the current simulation is not interactive");
        }
        return (InteractiveEnvironment) environment;
    }
}
