package utils;

import java.util.Map;
import java.util.stream.Collectors;

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
    private ConversionsUtil() { }
    /**
     * Convert a Food in ViewFood.
     * @param food Food to convert in ViewFood;
     * @return the converted ViewFood.
     */
    public static ViewFood conversionFromModelToView(final Food food) {
        final ViewFoodBuilder builder = new ViewFoodBuilder();
        food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n)))
                                    .entrySet().forEach(e -> builder.addNutrient(new Pair<>(e.getKey(), e.getValue())));
        return builder.setName(food.getName()).build();
    }
    /**
     * Convert a ViewFood in Food.
     * @param food ViewFood to convert.
     * @return the converted food.
     */
    public static Food conversionFromViewToModel(final ViewFood food) {
        final FoodFactory factory = new FoodFactoryImpl();
        return factory.createFoodFromNameAndNutrients(food.getName(), 
                food.getNutrients().stream().collect(Collectors.toMap(n -> n, n -> food.getQuantityFromNutrient(n))));
    }
    /**
     * Convert a State in ViewState.
     * @param state The state to convert.
     * @return the converted state.
     */
    public static ViewState conversionFromStateToViewState(final State state) {
        final Map<ViewPosition, ViewFood> foodState = state.getFoodsState()
                                                     .keySet()
                                                     .stream()
                                                     .collect(Collectors.toMap(p -> conversionFromPositionToViewPosition(p), 
                                                                               p -> conversionFromModelToView(state.getFoodsState().get(p))));
        return new ViewStateImpl(foodState); // TODO Aggiungere la mappa di bacteria state.
    }
    private static ViewPosition conversionFromPositionToViewPosition(final Position k) {
       return new ViewPositionImpl(k.getX(), k.getY());
    }

}
