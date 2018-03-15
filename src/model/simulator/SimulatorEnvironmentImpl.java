package model.simulator;

import java.util.stream.IntStream;

import model.Analisys;
import model.Position;
import model.State;
import model.StateImpl;
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
    private static final int FOOD_PER_ROUND = 15;
    // TODO this should probably not be static OR (?)
    private static final double COST_OF_LIVING = 2.0;
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    private final BacteriaManager bactManager = new BacteriaManagerImpl(foodEnv, COST_OF_LIVING);
    private final State state = new StateImpl(this.foodEnv);

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
    }

    @Override
    public Analisys getAnalisys() {
        // TODO Auto-generated method stub
        return null;
    }
}
