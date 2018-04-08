package model.simulator;

import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;

import model.Analysis;
import model.Environment;
import model.MutationManager;
import model.MutationManagerImpl;
import model.Position;
import model.PositionImpl;
import model.State;
import model.StateImpl;
import model.bacteria.Species;
import model.bacteria.SpeciesManager;
import model.bacteria.SpeciesManagerImpl;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
import utils.Log;

/**
 * implementation of Environment.
 *
 */
public class SimulatorEnvironment implements Environment {
    private final Position maxPosition = new PositionImpl(1000, 1000);
    private static final int FOOD_PER_ROUND = 15;
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    private BacteriaManager bactManager;
    private final SpeciesManager speciesManager = new SpeciesManagerImpl();
    private final MutationManager mutManager = new MutationManagerImpl();

    /**
     * Initializer method, create the default number of Bacteria.
     */
    public void init() {
        Log.getLog().info("Simulator initialized");
        this.bactManager = new BacteriaManagerImpl(foodEnv, maxPosition, speciesManager.getSpecies());
    }

    @Override
    public void addFood(final Food food, final Position position) {
        this.foodEnv.addFood(food, position);
    }

    @Override
    public Set<Food> getExistingFoods() {
        return Collections.unmodifiableSet(this.manager.getExistingFoodsSet());
    }

    @Override
    public State getState() {
        return new StateImpl(this.foodEnv.getFoodsState(), this.bactManager.getBacteriaState());
    }

    private void updateFood() {
        IntStream.range(0, FOOD_PER_ROUND).forEach(x -> this.foodEnv.addRandomFood());
    }

    private void updateBacteria() {
        this.bactManager.updateBacteria();
    }

    @Override
    public void update() {
        this.updateBacteria();
        this.updateFood();
    }

    @Override
    public Analysis getAnalisys() {
        // TODO Auto-generated method stub
        return null;
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
