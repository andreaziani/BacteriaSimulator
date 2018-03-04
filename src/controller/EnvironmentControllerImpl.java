package controller;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Environment;
import view.InitialState;
import view.ViewPosition;
import view.food.ViewFood;

/**
 * Implementation of EnvironmentController.
 *
 */
public class EnvironmentControllerImpl implements EnvironmentController {
    private final Environment env;
    private final FoodController foodController;
    /** 
     *  @param env the environment that controller will interact.
     */
    public EnvironmentControllerImpl(final Environment env) {
        this.env = env;
        this.foodController = new FoodControllerImpl(this.env);
    }

    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, position);
    }

    @Override
    public void start(final InitialState state) {
    }

    @Override
    public void addNewFood(final ViewFood food) {
        this.foodController.addNewFood(food);
    }

}
