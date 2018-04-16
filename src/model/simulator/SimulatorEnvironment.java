package model.simulator;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import model.Analysis;
import model.AnalysisImpl;
import model.Energy;
import model.EnergyImpl;
import model.InteractiveEnvironment;
import model.MutationManager;
import model.MutationManagerImpl;
import model.bacteria.species.SpeciesManager;
import model.bacteria.species.SpeciesManagerImpl;
import model.bacteria.species.SpeciesOptions;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.state.InitialState;
import model.state.Position;
import model.state.PositionImpl;
import model.state.State;
import model.state.StateImpl;
import utils.Logger;

/**
 * implementation of Environment.
 *
 */
public final class SimulatorEnvironment implements InteractiveEnvironment {
    private static final Energy INITIAL_ENERGY = new EnergyImpl(1000.0);
    private static final int FOOD_PER_ROUND = 2;
    private static final double DEFAULT_HEIGHT = 1000.0;
    private static final double DEFAULT_WIDTH = 1000.0;
    // pass on Constructor
    private final MutationManager mutManager = new MutationManagerImpl();
    private final ExistingFoodManager foodManager = new ExistingFoodManagerImpl();
    private final SpeciesManager speciesManager = new SpeciesManagerImpl();
    private final Analysis analysis = new AnalysisImpl();
    private final FoodEnvironment foodEnv;
    private BacteriaManager bactManager;
    private final Position maxPosition = new PositionImpl(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private InitialState initialState = new InitialState(this.maxPosition.getX(), this.maxPosition.getY());
    private Optional<State> state;

    /**
     * Create an empty environment.
     */
    public SimulatorEnvironment() {
        this.foodEnv = new FoodEnvironmentImpl(this.foodManager, this.maxPosition);
        this.bactManager = new BacteriaManagerImpl(this.foodEnv, this.foodManager, this.maxPosition, this.speciesManager);
        state = Optional.empty();
    }

    /**
     * Create an environment from an initial state. If the initial state has also
     * already a state it initialize the environment with that state.
     * 
     * @param initialState
     *            an InitialState.
     */
    public SimulatorEnvironment(final InitialState initialState) {
        this();
        this.initialState = initialState;
        initialState.getExistingFood().forEach(this.foodManager::addFood);
        initialState.getSpecies().forEach(this.speciesManager::addSpecies);
        if (initialState.hasState()) {
            initialize();
        }
    }

    @Override
    public void addFood(final Food food, final Position position) {
        this.foodEnv.addFood(food, position);
    }

    @Override
    public List<Food> getExistingFoods() {
        return Collections.unmodifiableList(this.foodManager.getExistingFoodsSet());
    }

    @Override
    public State getState() {
        if (!this.state.isPresent()) {
            this.state = Optional.of(new StateImpl(this.foodEnv.getFoodsState(), this.bactManager.getBacteriaState()));
        }
        return this.state.get();
    }

    private void updateFood() {
        IntStream.range(0, FOOD_PER_ROUND).forEach(x -> this.foodEnv.addRandomFood());
    }

    private void updateBacteria() {
        this.bactManager.updateBacteria();
    }

    private void updateMutation() {
        this.mutManager.updateMutation(bactManager.getBacteriaState().values());
    }

    @Override
    public void update() {
        this.updateBacteria();
        this.updateFood();
        this.updateMutation();
        this.state = Optional.of(new StateImpl(this.foodEnv.getFoodsState(), this.bactManager.getBacteriaState()));
        analysis.addState(state.get());
    }

    @Override
    public Analysis getAnalysis() {
        return this.analysis;
    }

    @Override
    public void addSpecies(final SpeciesOptions species) {
        speciesManager.addSpecies(species);
        this.initialState.addSpecies(species);
    }

    @Override
    public Position getMaxPosition() {
        return this.maxPosition;
    }

    @Override
    public void addNewTypeOfFood(final Food food) {
        this.foodManager.addFood(food);
        this.initialState.addFood(food);
    }

    @Override
    public void setFoodDistributionStrategy(final DistributionStrategy strategy) {
        this.foodEnv.setDistributionStrategy(strategy);
    }

    @Override
    public boolean isSimulationOver() {
        return getState().getBacteriaState().isEmpty();
    }

    @Override
    public void initialize() {
        try {
            Logger.getInstance().info("Environment", "Simulator initialized");
            if (this.initialState.hasState()) {
                state = Optional.of(initialState.getState().reconstructState(
                        s -> this.speciesManager.getSpeciesByName(s.getName()), () -> INITIAL_ENERGY));
                state.get().getFoodsState().forEach((position, food) -> this.foodEnv.addFood(food, position));
                this.bactManager.populate(Optional.of(this.state.get().getBacteriaState()));
            } else {
                this.bactManager.populate(Optional.empty());
            }
            this.initialState.setState(getState());
        } catch (NoSuchElementException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public InitialState getInitialState() {
        return this.initialState;
    }

}
