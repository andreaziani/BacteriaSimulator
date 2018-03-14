package controller.food;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Set;

import model.Environment;
import model.PositionImpl;
import model.food.ExistingFoodManager;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import utils.Pair;
import view.model.ViewPosition;
import view.model.food.ViewFood;
import view.model.food.ViewFoodImpl.ViewFoodBuilder;
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
        this.env.addFood(convertionFromViewToModel(food), new PositionImpl(position.getX(), position.getY()));
    }

    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return Collections.unmodifiableSet(manager.getExistingFoodsSet().stream().map(food -> convertionFromModelToView(food))
                                                                                 .collect(Collectors.toSet()));
    }

    private ViewFood convertionFromModelToView(final Food food) {
        final ViewFoodBuilder builder = new ViewFoodBuilder();
        food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n)))
                                    .entrySet().forEach(e -> builder.addNutrient(new Pair<>(e.getKey(), e.getValue())));
        return builder.setName(food.getName()).build();
    }

    private Food convertionFromViewToModel(final ViewFood food) {
        final FoodFactory factory = new FoodFactoryImpl();
        return factory.createFoodFromNameAndNutrients(food.getName(), 
                food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n))));
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.manager.addFood(convertionFromViewToModel(food));
    }
}
