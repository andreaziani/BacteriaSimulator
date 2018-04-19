package controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Analysis;
import model.Environment;
import model.InteractiveEnvironment;
import model.InvalidSpeciesExeption;
import model.bacteria.species.SpeciesOptions;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.replay.Replay;
import model.replay.ReplayEnvironment;
import model.simulator.SimulatorEnvironment;
import model.state.InitialState;
import model.state.State;
import utils.ConversionsUtil;
import utils.Logger;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.food.ViewFood;

/**
 * Implementation of EnvironmentController.
 *
 */
public abstract class EnvironmentControllerImpl implements EnvironmentController {
    private static final long PERIOD = 125L;
    private Environment environment;
    private FoodController foodController;
    private Optional<ViewPosition> maxViewPosition = Optional.empty();
    private Replay replay;
    private SimulationState currentState;
    private final SimulationLoop loop;

    /**
     * Constructor of EnvironmentController.
     */
    public EnvironmentControllerImpl() {

        initialize();
        this.loop = new SimulationLoop() {
            private boolean condition;

            @Override
            public void run() {
                condition = true;
                while (condition) {
                    final long start = System.currentTimeMillis();
                    State simulationState;
                    synchronized (EnvironmentControllerImpl.this) {
                        environment.update();
                        simulationState = environment.getState();
                        replay.addState(simulationState);
                        simulationLoop();
                        condition = !environment.isSimulationOver();
                    }
                    final long elapsed = System.currentTimeMillis() - start;
                    // DUBUGGING INFO
                    Logger.getInstance().info("GameLoop", "Elapsed: " + elapsed + " ms");
                    Logger.getInstance().info("GameLoop", "Bact size: " + simulationState.getBacteriaState().size());
                    Logger.getInstance().info("GameLoop", "Food size: " + simulationState.getFoodsState().size());
                    if (elapsed < PERIOD) {
                        try {
                            Thread.sleep(PERIOD - elapsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                updateCurrentState(SimulationState.ENDED);
                replay.setAnalysis(environment.getAnalysis());
            }

            @Override
            public void stop() {
                condition = false;
            }
        };
    }

    private void initialize() {
        this.currentState = SimulationState.NOT_READY;
        this.environment = new SimulatorEnvironment();
        this.foodController = new FoodControllerImpl(this.environment);
    }
    /**
     * Start the simulation from the initialState saved in this controller.
     */
    protected void startFromState() {
        this.startLoop();
    }

    @Override
    public synchronized void start() {
        this.startLoop();
    }

    private void startLoop() {
        this.updateCurrentState(SimulationState.RUNNING);
        this.environment.initialize();
        replay = new Replay(this.environment.getInitialState());
        final Thread mainThread = new Thread(this.loop);

        Logger.getInstance().info("Controller", "Application started");
        mainThread.start();
    }

    @Override
    public final void resetSimulation() {
        updateCurrentState(SimulationState.NOT_READY);
        initialize();
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
        this.resetSimulation();
        environment = new SimulatorEnvironment(initialState);
        foodController = new FoodControllerImpl(environment);
        if (initialState.getExistingFood().isEmpty() || initialState.getSpecies().isEmpty()) {
            this.updateCurrentState(SimulationState.NOT_READY);
        } else if (initialState.hasState()) { //Better to set in PAUSED
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
    protected abstract void simulationLoop();

    /**
     * @param replay
     *            a replay from which to construct a ReplayEnvironment.
     */
    protected void startReplay(final Replay replay) {
        environment = new ReplayEnvironment(replay);
        start();
    }

    @Override
    public synchronized void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, ConversionsUtil.viewPositionToPosition(position, environment.getMaxPosition(), maxViewPosition.get()));
    }

    @Override
    public synchronized void addNewTypeOfFood(final ViewFood food) {
        this.foodController.addNewTypeOfFood(food);

        if (this.currentState == SimulationState.NOT_READY && !this.isSpeciesEmpty()) {
            this.updateCurrentState(SimulationState.READY);
        }
    }

    @Override
    public synchronized List<ViewFood> getExistingViewFoods() {
        return this.foodController.getExistingViewFoods();
    }

    @Override
    public synchronized ViewState getState() {
        return ConversionsUtil.stateToViewState(this.environment.getState(), foodController,
                this.environment.getMaxPosition(), this.maxViewPosition.get(), environment.getInitialState());
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
    protected void updateCurrentState(final SimulationState state) {
        this.currentState = state;
    }

    @Override
    public Set<SpeciesOptions> getSpecies() {
        return this.getInitialState().getSpecies();
    }

    private InteractiveEnvironment getEnvironmentAsInteractive() {
        if (!(this.environment instanceof InteractiveEnvironment)) {
            throw new IllegalStateException();
        }
//        InteractiveEnvironment environment = (InteractiveEnvironment) this.environment;
        return (InteractiveEnvironment) environment;
    }
}
