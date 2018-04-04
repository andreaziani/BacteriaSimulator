package utils;

import java.awt.Color;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import controller.InitialState;
import controller.food.FoodController;
import model.Position;
import model.PositionImpl;
import model.State;
import model.bacteria.Bacteria;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import view.Radius;
import view.model.ViewPosition;
import view.model.ViewPositionImpl;
import view.model.ViewState;
import view.model.ViewStateImpl;
import view.model.bacteria.ViewBacteria;
import view.model.bacteria.ViewBacteriaImpl;
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
     * 
     * @param bacteria
     *            the Bacteria to convert.
     * @param initialState
     *            the initial state of the environment.
     * @param proportion
     *            the proportion of the radius of the bacteria.
     * @return the ViewBacteria corresponding to bacteria in the given initialState.
     * @throws NoSuchElementException
     *             if the Bacteria species is not present in the initialState.
     */
    public static ViewBacteria bacteriaToViewBacteria(final Bacteria bacteria, final double proportion,
            final InitialState initialState) {
        return new ViewBacteriaImpl(bacteria.getRadius() * proportion, initialState.getSpecies().stream()
                .filter(x -> x.getName().equals(bacteria.getSpecies().getName())).findFirst().get());
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
     * @param initialState
     *            the initial state of the environment.
     * @param proportion
     *            the proportion of the radius and positions.
     * @throws NoSuchElementException
     *             if the Bacteria species is not present in the initialState.
     * @return the converted state.
     */
    public static ViewState conversionFromStateToViewState(final State state, final FoodController fcontroller,
            final Position maxPosition, final ViewPosition maxViewPosition, final InitialState initialState,
            final double proportion) {
        final Map<ViewPosition, ViewFood> foodState = state.getFoodsState().keySet().stream()
                .collect(Collectors.toMap(p -> conversionFromPositionToViewPosition(p, maxPosition, maxViewPosition),
                        p -> conversionFromModelToView(state.getFoodsState().get(p),
                                fcontroller.getColorFromFood(state.getFoodsState().get(p)))));
        final Map<ViewPosition, ViewBacteria> bacteriaState = state.getBacteriaState().keySet().stream()
                .collect(Collectors.toMap(p -> conversionFromPositionToViewPosition(p, maxPosition, maxViewPosition),
                        p -> bacteriaToViewBacteria(state.getBacteriaState().get(p), proportion, initialState)));
        return new ViewStateImpl(foodState, bacteriaState);
    }

    /**
     * Convert a Position in ViewPosition.
     * 
     * @param pos
     *            the position to convert.
     * @param maxPosition
     *            the maximum position in environment.
     * @param maxViewPosition
     *            the maximum position in view.
     * @return the position converted in ViewPosition.
     */
    public static ViewPosition conversionFromPositionToViewPosition(final Position pos, final Position maxPosition,
            final ViewPosition maxViewPosition) {
        return new ViewPositionImpl(pos.getX() * maxViewPosition.getX() / maxPosition.getX(),
                pos.getY() * maxViewPosition.getY() / maxPosition.getY());
    }

    /**
     * Convert a ViewPosition in Position.
     * 
     * @param pos
     *            the ViewPosition to convert.
     * @param maxPosition
     *            the maximum position in the environment.
     * @param maxViewPosition
     *            the maximum position in the view.
     * @return the converted position.
     */
    public static Position conversionFromViewPositionToPosition(final ViewPosition pos, final Position maxPosition,
            final ViewPosition maxViewPosition) {
        return new PositionImpl(pos.getX() * maxPosition.getX() / maxViewPosition.getX(),
                pos.getY() * maxPosition.getY() / maxViewPosition.getY());
    }
    /**
     * Convert the model radius in view radius.
     * @param oldRadius the radius in the model.
     * @param maxPosition the max position in the model environment.
     * @param maxViewPosition the max position in the view.
     * @return the new Radius for the view.
     */
    public static Radius conversionRadius(final double oldRadius, final Position maxPosition, final ViewPosition maxViewPosition) {
        return new Radius((int) (oldRadius * maxViewPosition.getX() / maxPosition.getX()), (int) (oldRadius * maxViewPosition.getY() / maxPosition.getY()));
    }
}
