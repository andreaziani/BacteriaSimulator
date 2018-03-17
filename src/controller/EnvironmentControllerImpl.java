package controller;

import java.util.Set;

import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Environment;
import model.bacteria.SpeciesBuilder;
import utils.ConversionsUtil;
import utils.exceptions.AlreadyExistingSpeciesExeption;
import utils.exceptions.InvalidSpeicesExeption;
import utils.exceptions.SimulationAlreadyStartedExeption;
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
     * @param env
     *            the environment that controller will interact.
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
        // TODO start
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
        if (isSimulationStarted()) {
            throw new SimulationAlreadyStartedExeption();
        }
        final SpeciesBuilder builder = new SpeciesBuilder();
        try {
            builder.setName(species.getName());
            species.getDecisionOptions().forEach(builder::addDecisionMaker);
            species.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
            env.getSpeciesManager().addSpecies(builder.build());
        } catch (IllegalStateException | AlreadyExistingSpeciesExeption e) {
            throw new InvalidSpeicesExeption();
        }
    }

    private boolean isSimulationStarted() {
        // TODO Auto-generated method stub
        return false;
    }
}
