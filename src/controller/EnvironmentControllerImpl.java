package controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Analysis;
import model.EnergyImpl;
import model.Environment;
import model.Position;
import model.State;
import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.food.Food;
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
    private Environment environment;
    private FoodController foodController;
    private Optional<ViewPosition> maxViewPosition = Optional.empty();
    private InitialState initialState;
    private Replay replay;
    private SimulationState currentState;
    private final SimulationLoop loop;

    /**
     * Constructor of EnvironmentController.
     */
    public EnvironmentControllerImpl() {

        initialize();
        this.loop = new SimulationLoop() {
            @Override
            public void run() {
                boolean condition = true;
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
        };
    }

    private void initialize() {
        this.currentState = SimulationState.NOT_READY;
        this.environment = new SimulatorEnvironment();
        this.foodController = new FoodControllerImpl(this.environment);
        initialState = new InitialState(environment.getMaxPosition().getX(), environment.getMaxPosition().getY());
    }

    private void setParameter() {
        final Set<Food> existingFood = this.initialState.getExistingFood().stream()
                .map(viewFood -> ConversionsUtil.viewFoodToFood(viewFood)).collect(Collectors.toSet());

        final Set<Species> existingSpecies = this.initialState.getSpecies().stream().map(viewSpecie -> {
            final SpeciesBuilder builder = new SpeciesBuilder(viewSpecie.getName());
            viewSpecie.getDecisionOptions().forEach(builder::addDecisionMaker);
            viewSpecie.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
            return builder.build();
        }).collect(Collectors.toSet());

        this.environment.setSimulationParameter(existingFood, existingSpecies);
    }

    private void setState() {
        this.setParameter();
        final Position maxPosition = this.initialState.getMaxPosition();
        final SimpleState simulationState = this.initialState.getState();
        this.environment.setSimulationState(simulationState, maxPosition);
    }

    /**
     * Start the simulation from the initialState saved in this controller.
     */
    protected void startFromState() {
        this.setState();
        this.startLoop();
    }

    @Override
    public synchronized void start() {
        this.setParameter();
        this.startLoop();
    }

    private void startLoop() {
        this.updateCurrentState(SimulationState.RUNNING);
        this.environment.initialize();
        this.initialState.setState(environment.getState());
        replay = new Replay(this.initialState);
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
        initialState = replay.getInitialState();
        environment = new ReplayEnvironmentImpl(initialState,
                replay.getStateList().stream().map(
                        x -> x.reconstructState(s -> new SpeciesBuilder(s.getName()).build(), () -> EnergyImpl.ZERO))
                        .iterator(),
                replay.getAnalysis());
        start();
    }

    @Override
    public synchronized void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, ConversionsUtil.viewPositionToPosition(position, environment.getMaxPosition(), maxViewPosition.get()));
    }

    @Override
    public synchronized void addNewTypeOfFood(final ViewFood food) {
        //this.foodController.addNewTypeOfFood(food);
        initialState.addFood((CreationViewFoodImpl) food);
        if (this.currentState == SimulationState.NOT_READY && !this.isSpeciesEmpty()) {
            this.updateCurrentState(SimulationState.READY);
        }
    }

    @Override
    public synchronized List<CreationViewFoodImpl> getExistingViewFoods() {
        return this.initialState.getExistingFood();
        //return this.foodController.getExistingViewFoods();
    }

    @Override
    public synchronized ViewState getState() {
        return ConversionsUtil.stateToViewState(this.environment.getState(), foodController,
                this.environment.getMaxPosition(), this.maxViewPosition.get(), initialState);
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
            //environment.addSpecies(builder.build());
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
        this.environment.setFoodDistributionStrategy(strategy);
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
