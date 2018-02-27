package controller.food;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Set;

import model.Environment;
import model.PositionImpl;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodImpl.FoodBuilder;
import view.ViewPosition;
import view.food.ViewFood;
import view.food.ViewFoodImpl.ViewFoodBuilder;
/**
 * Controller for food.
 *
 */
public class FoodControllerImpl implements FoodController {
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final Environment env;
    /**
     * 
     * @param env environment.
     */
    public FoodControllerImpl(final Environment env) {
        this.env = env;
    }
    
    @Override
    public void addFoodFromViewToModel(final ViewFood food, final ViewPosition position) {
        this.env.addFood(manager.getExistingFoodsMap().get(food.getName()), new PositionImpl(position.getX(), position.getY()));
    }
    
    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return Collections.unmodifiableSet(manager.getExistingFoodsMap()
                                                  .keySet()
                                                  .stream()
                                                  .map(k -> convertionFromModelToView(k, manager.getExistingFoodsMap().get(k)))
                                                  .collect(Collectors.toSet()));
    }

    // name = nome del cibo in viewfood, food cibo da convertire in viewFood.
    private ViewFood convertionFromModelToView(final String name, final Food food) {
        final ViewFoodBuilder builder = new ViewFoodBuilder();
//        final Map<Nutrient, Double> nutrients = new HashMap<>();
        food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n)))
                                    .entrySet().forEach(e -> builder.addNutrient(e));
//        food.getNutrients().forEach(n -> nutrients.put(n, food.getQuantityFromNutrient(n)));
//        nutrients.entrySet().forEach(e -> builder.addNutrient(e));
        return builder.setName(name).build();
    }

    private Food convertionFromViewToModel(final ViewFood food) {
        final FoodBuilder builder = new FoodBuilder();
        food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n)))
                                    .entrySet().forEach(e -> builder.addNutrient(e));
        return builder.build();
    }

    @Override
    public void addNewFood(final ViewFood food) {
        this.manager.addFood(food.getName(), convertionFromViewToModel(food));
    }
}
