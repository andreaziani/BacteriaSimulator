package model;

import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
/**
 * Implementation of Environment. 
 *
 */
public class EnvironmentImpl implements Environment {
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    @Override
    public void addFood(final Food food, final Position position) {
        this.foodEnv.addFood(food, position);
    }

    @Override
    public State getState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update() {
        this.foodEnv.addRandomFood();
        // TODO Auto-generated method stub

    }

    @Override
    public Analisys getAnalisys() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExistingFoodManager getExistingFoods() {
        return this.manager;
    }

}
