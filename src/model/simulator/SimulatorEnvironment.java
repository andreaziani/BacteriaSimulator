package model.simulator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import controller.InitialState;
import model.Analysis;
import model.AnalysisImpl;
import model.Environment;
import model.MutationManager;
import model.MutationManagerImpl;
import model.Position;
import model.PositionImpl;
import model.State;
import model.StateImpl;
import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.bacteria.SpeciesManager;
import model.bacteria.SpeciesManagerImpl;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
import utils.ConversionsUtil;
import utils.Log;

/**
 * implementation of Environment.
 *
 */
public class SimulatorEnvironment implements Environment {
    private static final int FOOD_PER_ROUND = 5;
    // pass on Constructor
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    private final SpeciesManager speciesManager = new SpeciesManagerImpl();
    private final MutationManager mutManager = new MutationManagerImpl();
    private final Analysis analysis = new AnalysisImpl();
    private BacteriaManager bactManager;
    private Position maxPosition = new PositionImpl(1000.0, 1000.0);
    private State state;

    /**
     * Initializer method, create the default number of Bacteria.
     * @param initialState
     *          the initial state used to initialize the simulation
     */
    public void init(final Optional<InitialState> initialState) {
        Log.getLog().info("Simulator initialized");
        if (initialState.isPresent()) {
            // add existing food
            initialState.get().getExistingFood().forEach(creationViewFood -> {
                final Food food = ConversionsUtil.conversionFromViewToModel(creationViewFood);
                this.manager.addFood(food);
            });
            // add food
            initialState.get().getFoodMap().entrySet().forEach(entry -> {
                final Food food = ConversionsUtil.conversionFromViewToModel(entry.getValue());
                this.foodEnv.addFood(food, entry.getKey());
            });
            // add existing species
            initialState.get().getSpecies().stream().forEach(viewSpecie -> {
                final SpeciesBuilder builder = new SpeciesBuilder(viewSpecie.getName());
                viewSpecie.getDecisionOptions().forEach(builder::addDecisionMaker);
                viewSpecie.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
                this.addSpecies(builder.build());
            });
            // set max position
            this.maxPosition = initialState.get().getMaxPosition();
            // add bacteria
            /*
             *      TODO complete creation of Bacteria
             */
            this.bactManager = new BacteriaManagerImpl(Optional.empty(), speciesManager.getSpecies(), foodEnv, manager, maxPosition);
        } else {
            this.maxPosition = new PositionImpl(1000, 1000);
            this.bactManager = new BacteriaManagerImpl(Optional.empty(), speciesManager.getSpecies(), foodEnv, manager, maxPosition);
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
}
