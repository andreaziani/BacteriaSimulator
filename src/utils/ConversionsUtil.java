package utils;

import java.awt.Color;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import controller.food.FoodController;
import model.Position;
import model.State;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import view.model.ViewPosition;
import view.model.ViewPositionImpl;
import view.model.ViewState;
import view.model.ViewStateImpl;
import view.model.food.ViewFood;
import view.model.food.ViewFoodImpl.ViewFoodBuilder;

/**
 * Utility Class for conversions of types.
 *
 */
public final class ConversionsUtil {
    private ConversionsUtil() {
    }

    /**
     * Convert a Food in ViewFood.
     * 
     * @param food
     *            the Food to convert in ViewFood;
     * @param color
     *            the color of the food.
     * @return the converted ViewFood.
     */
    public static ViewFood conversionFromModelToView(final Food food, final Color color) {
        final ViewFoodBuilder builder = new ViewFoodBuilder(food.getName());
        builder.addColor(color);
        food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n))).entrySet()
                .forEach(e -> builder.addNutrient(Pair.of(e.getKey(), e.getValue())));
        return builder.build();
    }

    /**
     * Convert a ViewFood in Food.
     * 
     * @param food
     *            the ViewFood to convert in Food.
     * @return the converted Food.
     */
    public static Food conversionFromViewToModel(final ViewFood food) {
        final FoodFactory factory = new FoodFactoryImpl();
        return factory.createFoodFromNameAndNutrients(food.getName(),
                food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n))));
    }

    /**
     * Convert a State in ViewState.
     * 
     * @param state
     *            the state to convert.
     * @param fcontroller
     *            FoodController that contains color for each food.
     * @param maxPosition
     *            the maximum position in the environment.
     * @param maxViewPosition
     *            the maximum position in the view.
     * @return the converted state.
     */
    public static ViewState conversionFromStateToViewState(final State state, final FoodController fcontroller,
            final Position maxPosition, final ViewPosition maxViewPosition) {
        final Map<ViewPosition, ViewFood> foodState = state.getFoodsState().keySet().stream()
                .collect(Collectors.toMap(p -> conversionFromPositionToViewPosition(p, maxPosition, maxViewPosition),
                        p -> conversionFromModelToView(state.getFoodsState().get(p),
                                fcontroller.getColorFromFood(state.getFoodsState().get(p)))));
        return new ViewStateImpl(foodState); // TODO Aggiungere la mappa di bacteria state.
    }

    private static ViewPosition conversionFromPositionToViewPosition(final Position k, final Position maxPosition,
            final ViewPosition maxViewPosition) {
        return new ViewPositionImpl(k.getX() * maxPosition.getX() / maxViewPosition.getX(),
                k.getY() * maxPosition.getY() / maxViewPosition.getY());
    }

}
