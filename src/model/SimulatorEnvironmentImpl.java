package model;

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
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    @Override
    public void addFood(final Food food, final Position position) {
        // TODO Auto-generated method stub
        this.foodEnv.addFood(food, position);
    }

    @Override
    public ExistingFoodManager getExistingFoods() {
        // TODO Auto-generated method stub
        return this.manager;
    }

    @Override
    public State getState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public Analisys getAnalisys() {
        // TODO Auto-generated method stub
        return null;
    }

}
