package controller.food;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import model.Environment;
import model.Position;
import utils.ConversionsUtil;
import view.model.food.ViewFood;

/**
 * Implementation of FoodController, it manages food interactions.
 *
 */
public class FoodControllerImpl implements FoodController {
    private final Environment env;

    /**
     * Constructor that build the controller by passing the Environment on which it
     * must manage the foods.
     * 
     * @param env
     *            the environment on which the Controller must manage foods.
     */
    public FoodControllerImpl(final Environment env) {
        this.env = env;
    }

    @Override
    public void addFoodFromViewToModel(final ViewFood food, final Position position) {
        this.env.addFood(ConversionsUtil.conversionFromViewToModel(food), position);
    }

    @Override
    public List<ViewFood> getExistingViewFoods() {
        return Collections.unmodifiableList(env.getExistingFoods().stream()
                .map(food -> ConversionsUtil.conversionFromModelToView(food))
                .collect(Collectors.toList()));
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.env.addNewTypeOfFood(ConversionsUtil.conversionFromViewToModel(food));
    }
}
