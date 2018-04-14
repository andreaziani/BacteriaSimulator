package controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Analysis;
import model.EnergyImpl;
import model.Environment;
import model.bacteria.SpeciesBuilder;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.replay.ReplayEnvironmentImpl;
import model.simulator.SimulatorEnvironment;
import utils.ConversionsUtil;
import utils.Logger;
import utils.exceptions.InvalidSpeciesExeption;
import utils.exceptions.SimulationAlreadyStartedExeption;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.food.CreationViewFoodImpl;
import view.model.food.ViewFood;

/**
 * Implementation of EnvironmentController.
 *
 */
public abstract class EnvironmentControllerImpl implements EnvironmentController {
    private static final long PERIOD = 125L;
    private Environment env;
    private FoodController foodController;
    private Optional<ViewPosition> maxViewPosition = Optional.empty();
    private InitialState initialState;
    private Replay replay;
    private SimulationState currentState = SimulationState.NOT_READY;
    private final SimulationLoop loop;

    /**
     * Constructor of EnvironmentController.
     */
    public EnvironmentControllerImpl() {

        resetSimulation();
        this.loop = new SimulationLoop() {
            @Override
            public void run() {
                boolean condition = true;
                while (condition) {
                    final long start = System.currentTimeMillis();
                    synchronized (EnvironmentControllerImpl.this) {
                        env.update();
                        replay.addState(env.getState());
                        simulationLoop();
                        condition = !env.isSimulationOver();
                    }
                    final long elapsed = System.currentTimeMillis() - start;
                    System.out.println(elapsed + " ms");
                    if (elapsed < PERIOD) {
                        try {
                            Thread.sleep(PERIOD - elapsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                updateCurrentState(SimulationState.ENDED);
                replay.setAnalysis(env.getAnalysis());
            }
        };
    }

    private void resetSimulation() {
        this.currentState = SimulationState.NOT_READY;
        this.env = new SimulatorEnvironment();
        this.foodController = new FoodControllerImpl(this.env);
        initialState = new InitialState(env.getMaxPosition().getX(), env.getMaxPosition().getY());
    }

    /**
     * Restore the simulation to a state defined by an InitialState object.
     * 
     * @param initialState
     *            the representation of the initial state.
     */
    protected void setInitialState(final InitialState initialState) {
        this.initialState = initialState;
        if (initialState.getExistingFood().isEmpty() || initialState.getSpecies().isEmpty()) {
            this.updateCurrentState(SimulationState.NOT_READY);
        } else if (initialState.hasState()) {
            this.start();
        } else {
            this.updateCurrentState(SimulationState.READY);
        }
    }

    /**
     * Start the simulation from the initialState saved in this controller.
     */
    protected void startFromInitialState() {
        // resetSimulation(); if reset HERE all the parameters (species, food types ..)
        // get deleted
        this.startLoop(Optional.of(this.initialState));
        start();
    }

    /**
     * @return the initial state of the simulation.
     */
    protected InitialState getInitialState() {
        return initialState;
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
    protected Analysis getAnalysis() {
        return env.getAnalysis();
    }

    /**
     * method intended for allowing EnvController and Controller to "communicate".
     */
    protected abstract void simulationLoop();

    private void startLoop(final Optional<InitialState> initialState) {
        this.updateCurrentState(SimulationState.RUNNING);
        this.env.init(initialState);
        this.initialState.setState(env.getState());
        replay = new Replay(this.initialState);
        final Thread mainThread = new Thread(this.loop);
        Logger.getLog().info("Application started");
        mainThread.start();
    }

    @Override
    public synchronized void start() {
        // resetSimulation(); if reset HERE all the parameters (species, food types ..)
        // get deleted
        this.startLoop(Optional.empty());
    }

    /**
     * @param replay
     *            a replay from which to construct a ReplayEnvironment.
     */
    protected void startReplay(final Replay replay) {
        initialState = replay.getInitialState();
        env = new ReplayEnvironmentImpl(initialState,
                replay.getStateList().stream().map(
                        x -> x.reconstructState(s -> new SpeciesBuilder(s.getName()).build(), () -> EnergyImpl.ZERO))
                        .iterator(),
                replay.getAnalysis());
    }

    @Override
    public synchronized void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, ConversionsUtil.conversionFromViewPositionToPosition(position,
                env.getMaxPosition(), maxViewPosition.get()));
    }

    @Override
    public synchronized void addNewTypeOfFood(final ViewFood food) {
        this.foodController.addNewTypeOfFood(food);
        initialState.addFood((CreationViewFoodImpl) food);
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
        return ConversionsUtil.conversionFromStateToViewState(this.env.getState(), foodController,
                this.env.getMaxPosition(), this.maxViewPosition.get(), initialState);
    }

    @Override
    public synchronized void addSpecies(final ViewSpecies species) {
        try {
            if (this.currentState == SimulationState.NOT_READY && !this.getExistingViewFoods().isEmpty()) {
                this.updateCurrentState(SimulationState.READY);
            }
            final SpeciesBuilder builder = new SpeciesBuilder(species.getName());
            species.getDecisionOptions().forEach(builder::addDecisionMaker);
            species.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
            env.addSpecies(builder.build());
            initialState.addSpecies(species);
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
        return this.initialState.getSpecies().isEmpty();
    }

    @Override
    public void setDistributionStrategy(final DistributionStrategy strategy) {
        this.env.setFoodDistributionStrategy(strategy);
    }

    @Override
    public void updateCurrentState(final SimulationState state) {
        this.currentState = state;
    }

    @Override
    public Set<ViewSpecies> getSpecies() {
        return this.initialState.getSpecies();
    }
}
