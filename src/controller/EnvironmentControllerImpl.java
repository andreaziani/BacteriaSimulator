package controller;

import java.util.Set;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Environment;
import model.bacteria.SpeciesBuilder;
import utils.ConversionsUtil;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;

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
    public void start() {
        //TODO start 
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.foodController.addNewTypeOfFood(food);
    }

    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return this.foodController.getExistingViewFoods();
    }

    @Override
    public ViewState getState() {
        return ConversionsUtil.conversionFromStateToViewState(this.env.getState());
    }

    @Override
    public void addSpecies(final ViewSpecies species) {
        //TODO
    }

}
