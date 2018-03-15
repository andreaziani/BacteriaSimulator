package controller.food;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Set;

import model.Environment;
import model.PositionImpl;
import model.food.ExistingFoodManager;
import utils.ConversionsUtil;
import view.model.ViewPosition;
import view.model.food.ViewFood;
/**
 * Controller for food.
 *
 */
public class FoodControllerImpl implements FoodController {
    private final Environment env;
    private final ExistingFoodManager manager;
    /**
     * Constructor that build the controller from an Environment from 
     * which also get the ExistingFoodManager.
     * @param env environment.
     */
    public FoodControllerImpl(final Environment env) {
        this.env = env;
        manager = env.getExistingFoods();
    }

    @Override
    public void addFoodFromViewToModel(final ViewFood food, final ViewPosition position) {
        this.env.addFood(ConversionsUtil.convertionFromViewToModel(food), new PositionImpl(position.getX(), position.getY()));
    }

    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return Collections.unmodifiableSet(manager.getExistingFoodsSet().stream().map(food -> ConversionsUtil.convertionFromModelToView(food))
                                                                                 .collect(Collectors.toSet()));
    }


    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.manager.addFood(ConversionsUtil.convertionFromViewToModel(food));
    }
}
