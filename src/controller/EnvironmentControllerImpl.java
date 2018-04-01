package controller;

import java.util.Optional;
import java.util.Set;
import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Environment;
import model.Position;
import model.bacteria.SpeciesBuilder;
import utils.ConversionsUtil;
import utils.exceptions.InvalidSpeciesExeption;
import utils.exceptions.SimulationAlreadyStartedExeption;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;
import view.model.food.ViewFoodImpl;

/**
 * Implementation of EnvironmentController.
 *
 */
public class EnvironmentControllerImpl implements EnvironmentController {
    private final Environment env;
    private final FoodController foodController;
    private final Position maxPosition;
    private Optional<ViewPosition> maxViewPosition = Optional.empty();
    private final InitialState initialState;

    /**
     * Constructor that builds the EnvironmentController by passing the Environment
     * as an argument.
     * 
     * @param env
     *            the environment that the Controller must manage.
     */
    public EnvironmentControllerImpl(final Environment env) {
        this.env = env;
        this.maxPosition = env.getMaxPosition();
        this.foodController = new FoodControllerImpl(this.env);
        initialState = new InitialState();
    }

    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, ConversionsUtil.conversionFromViewPositionToPosition(position,
                env.getMaxPosition(), maxViewPosition.get()));
//        System.out.println(ConversionsUtil.conversionFromViewPositionToPosition(position,
//                env.getMaxPosition(), maxViewPosition.get()).getX() + " " + ConversionsUtil.conversionFromViewPositionToPosition(position,
//                        env.getMaxPosition(), maxViewPosition.get()).getY());

    }

    @Override
    public void start() {
        // TODO start
        //TODO complete InitialState
    }

    @Override
    public void startFromInitialState() {
        //TODO resettare env, reinserire tutto da InitialState e fare start
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.foodController.addNewTypeOfFood(food);
        initialState.addFood((ViewFoodImpl) food);
    }

    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return this.foodController.getExistingViewFoods();
    }

    @Override
    public ViewState getState() {
        return ConversionsUtil.conversionFromStateToViewState(this.env.getState(), foodController, this.maxPosition,
                this.maxViewPosition.get());
    }

    @Override
    public void addSpecies(final ViewSpecies species) {
        if (isSimulationStarted()) {
            throw new SimulationAlreadyStartedExeption();
        }
        try {
            final SpeciesBuilder builder = new SpeciesBuilder(species.getName());
            species.getDecisionOptions().forEach(builder::addDecisionMaker);
            species.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
            env.addSpecies(builder.build());
            initialState.addSpecies(species);
        } catch (IllegalStateException e) {
            throw new InvalidSpeciesExeption();
        }
    }

    private boolean isSimulationStarted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setMaxViewDimension(final ViewPosition position) {
        this.maxViewPosition = Optional.of(position);
        initialState.setMaxX(position.getX());
        initialState.setMaxY(position.getY());
    }
}
