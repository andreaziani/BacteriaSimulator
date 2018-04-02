package model.simulator;

import java.util.stream.IntStream;

import model.Analysis;
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

/**
 * implementation of SimulatorEnvironment.
 *
 */
public class SimulatorEnvironmentImpl implements SimulatorEnvironment {
    private final Position maxPosition = new PositionImpl(1000, 1000);
    private static final int FOOD_PER_ROUND = 15;
    // TODO this should probably not be static OR (?)
    private static final double COST_OF_LIVING = 2.0;
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    private final BacteriaManager bactManager = new BacteriaManagerImpl(foodEnv, COST_OF_LIVING);
    private final SpeciesManager speciesManager = new SpeciesManagerImpl();
    private State state;

    @Override
    public void addFood(final Food food, final Position position) {
        this.foodEnv.addFood(food, position);
    }

    @Override
    public ExistingFoodManager getExistingFoods() {
        return this.manager;
    }

    @Override
    public State getState() {
        return this.state;
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
        this.state = new StateImpl(this.foodEnv.getFoodsState()); //TODO aggiungere gli state di bacteria
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
}
