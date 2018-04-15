package model.simulator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import controller.InitialState;
import controller.SimpleState;
import model.Analysis;
import model.AnalysisImpl;
import model.Energy;
import model.EnergyImpl;
import model.Environment;
import model.MutationManager;
import model.MutationManagerImpl;
import model.Position;
import model.PositionImpl;
import model.State;
import model.StateImpl;
import model.bacteria.Bacteria;
import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.bacteria.SpeciesManager;
import model.bacteria.SpeciesManagerImpl;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
import model.food.insertionstrategy.position.DistributionStrategy;
import utils.ConversionsUtil;
import utils.Logger;

/**
 * implementation of Environment.
 *
 */
public class SimulatorEnvironment implements Environment {
    private static final Energy INITIAL_ENERGY = new EnergyImpl(1000.0);
    private static final int FOOD_PER_ROUND = 2;
    private static final double DEFAULT_HEIGHT = 1000.0;
    private static final double DEFAULT_WIDTH = 1000.0;
    // pass on Constructor
    private final MutationManager mutManager = new MutationManagerImpl();
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final SpeciesManager speciesManager = new SpeciesManagerImpl();
    private final Analysis analysis = new AnalysisImpl();
    private final FoodEnvironment foodEnv;
    private BacteriaManager bactManager;
    private Position maxPosition = new PositionImpl(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private Optional<State> initState = Optional.empty();
    private State state;

    public SimulatorEnvironment() {
        this.foodEnv = new FoodEnvironmentImpl(this.manager, this.maxPosition);
        this.bactManager = new BacteriaManagerImpl(this.foodEnv, this.manager, this.maxPosition, this.speciesManager);
    }

    /**
     * Initializer method, create the default number of Bacteria.
     * @param initialState
     *          the initial state used to initialize the simulation
     */

    public void setSimulationParameter(final Set<Food> existingFood, final Set<Species> existingSpecies) {
        existingFood.forEach(food -> this.manager.addFood(food));
        existingSpecies.forEach(species -> this.speciesManager.addSpecies(species));
    }

    public void setSimulationState(final SimpleState simulationState, final Position maxPosition) {
        this.initState = Optional.of(simulationState.reconstructState(species -> this.speciesManager.getSpeciesByName(species.getName()), () -> INITIAL_ENERGY));
        this.maxPosition = maxPosition;
    }

    public void initialize() {
        Logger.getInstance().info("Environment", "Simulator initialized");
        if (this.initState.isPresent()) {
            this.initState.get().getFoodsState().forEach((position, food) -> this.foodEnv.addFood(food, position));
            this.bactManager.populate(Optional.of(this.initState.get().getBacteriaState()));
        } else {
            this.bactManager.populate(Optional.empty());
        }
    }

    @Override
    public void addFood(final Food food, final Position position) {
        this.foodEnv.addFood(food, position);
    }

    @Override
    public List<Food> getExistingFoods() {
        return Collections.unmodifiableList(this.manager.getExistingFoodsSet());
    }

    @Override
    public State getState() {
        if (this.state == null) {
            this.state = new StateImpl(this.foodEnv.getFoodsState(), this.bactManager.getBacteriaState());
        }
        return this.state;
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
        this.state = new StateImpl(this.foodEnv.getFoodsState(), this.bactManager.getBacteriaState());
        analysis.addState(state);
    }

    @Override
    public Analysis getAnalysis() {
        return this.analysis;
    }

    @Override
    public void addSpecies(final Species species) {
        speciesManager.addSpecies(species);
    }

    @Override
    public Position getMaxPosition() {
        return this.maxPosition;
    }

    @Override
    public void addNewTypeOfFood(final Food food) {
        this.manager.addFood(food);
    }

    @Override
    public void setFoodDistributionStrategy(final DistributionStrategy strategy) {
        this.foodEnv.setDistributionStrategy(strategy);
    }

    @Override
    public boolean isSimulationOver() {
        return getState().getBacteriaState().isEmpty();
    }
}
