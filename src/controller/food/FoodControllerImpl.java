package controller.food;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;

import model.Environment;
import model.Position;
import model.food.Food;
import utils.ConversionsUtil;
import view.model.food.ViewFood;

/**
 * Implementation of FoodController, it manages food interactions.
 *
 */
public class FoodControllerImpl implements FoodController {
    private final Environment env;
    private final Map<String, Color> colorForFood = new HashMap<>();

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
    public Set<ViewFood> getExistingViewFoods() {
        return Collections.unmodifiableSet(env.getExistingFoods().stream()
                .map(food -> ConversionsUtil.conversionFromModelToView(food, getColorFromFood(food)))
                .collect(Collectors.toSet()));
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.env.addNewTypeOfFood(ConversionsUtil.conversionFromViewToModel(food));
        this.colorForFood.put(food.getName(), food.getColor());
    }

    @Override
    public Color getColorFromFood(final Food food) {
        if (food.getName() == null || this.colorForFood.containsKey(food.getName())) {
            return Color.black;
        }
        return this.colorForFood.get(food.getName());
    }
}
