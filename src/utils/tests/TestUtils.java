package utils.tests;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.Direction;
import model.Energy;
import model.EnergyImpl;
import model.action.Action;
import model.action.ActionType;
import model.bacteria.behavior.BaseDecisionBehavior;
import model.bacteria.behavior.decisionmaker.DecisionMakerFactory;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;

/**
 * Utility class that provide methods useful for other test classes.
 */
public final class TestUtils {
    private static final double DELTA = 0.0625;
    private static final double V1 = 10.2;
    private static final double V2 = 0.2;
    private static final double V3 = 13.1;
    private static final double SMALL_DOUBLE = 1;
    private static final double LARGE_DOUBLE = 10;

    private static final Energy SMALL_ENERGY = new EnergyImpl(SMALL_DOUBLE);
    private static final Energy LARGE_ENERGY = new EnergyImpl(LARGE_DOUBLE);

    private static final Food FOOD_1;
    private static final Food FOOD_2;

    static {
        final FoodFactory factory = new FoodFactoryImpl();
        Map<Nutrient, Double> nutrients = new HashMap<>();
        nutrients.put(Nutrient.CARBOHYDRATES, V1);
        nutrients.put(Nutrient.HYDROLYSATES, V2);
        nutrients.put(Nutrient.PEPTONES, V3);
        FOOD_1 = factory.createFoodFromNutrients(nutrients);

        nutrients = new HashMap<>();
        nutrients.put(Nutrient.WATER, V1);
        nutrients.put(Nutrient.INORGANIC_SALT, V2);
        FOOD_2 = factory.createFoodFromNutrients(nutrients);
    }

    private TestUtils() {
    }

    /**
     * @return a delta to compare double.
     */
    public static double getDoubleCompareDelta() {
        return DELTA;
    }

    /**
     * @return a small double value (compared to the large double).
     */
    public static double getSmallDouble() {
        return SMALL_DOUBLE;
    }

    /**
     * @return a large double value (compared to the small double).
     */
    public static double getLargeDouble() {
        return LARGE_DOUBLE;
    }

    /**
     * @return a small Energy value (compared to the large energy).
     */
    public static Energy getSmallEnergy() {
        return SMALL_ENERGY;
    }

    /**
     * @return a large Energy value (compared to the small energy).
     */
    public static Energy getLargeEnergy() {
        return LARGE_ENERGY;
    }

    /**
     * @return a Food, without being interested in its content. The only requirement
     *         is that all the Food returned by this method will be considered
     *         equals.
     */
    public static Food getAFood() {
        return FOOD_1;
    }

    /**
     * @param food
     *            a Food that the return value must not equal.
     * @return a food that is not equal to the food given as input.
     */
    public static Food getNotEqualFood(final Food food) {
        return food.equals(FOOD_1) ? FOOD_2 : FOOD_1;
    }

    /**
     * @param type
     *            an ActionType.
     * @return a function from actions to energy that return a small amount of
     *         Energy if the action is of the given type and a large amount of
     *         energy otherwise.
     */
    public static Function<Action, Energy> singleLowCostActionType(final ActionType type) {
        return x -> x.getType().equals(type) ? SMALL_ENERGY : LARGE_ENERGY;
    }

    /**
     * @param type
     *            an ActionType.
     * @return a function from actions to energy that return a large amount of
     *         Energy if the action is of the given type and a small amount of
     *         energy otherwise.
     */
    public static Function<Action, Energy> singleLargeCostActionType(final ActionType type) {
        return x -> x.getType().equals(type) ? LARGE_ENERGY : SMALL_ENERGY;
    }

    /**
     * @param dir
     *            a Direction.
     * @return a map from directions to double that associates a large value to the
     *         given direction and a small value to all the others.
     */
    public static Map<Direction, Double> bestDirection(final Direction dir) {
        final Map<Direction, Double> result = new EnumMap<>(Direction.class);
        for (final Direction d : Direction.values()) {
            if (d.equals(dir)) {
                result.put(d, LARGE_DOUBLE);
            } else {
                result.put(d, SMALL_DOUBLE);
            }
        }
        return result;
    }

    /**
     * @return a fuction that assign a small value to all the nutrients.
     */
    public static Function<Nutrient, Energy> allNutrientGood() {
        return x -> SMALL_ENERGY;
    }

    /**
     * @return a fuction that assign a negative small value to all the nutrients.
     */
    public static Function<Nutrient, Energy> allNutrientsBad() {
        return x -> SMALL_ENERGY.invert();
    }

    /**
     * Create a BaseDecisionBehavior giving a list of DecisionMakerOptions.
     * 
     * @param options
     *            a list of decision maker options.
     * @return a new BaseDecisionBehavior with the specified decisionMakers.
     */
    public static BaseDecisionBehavior baseBehaviorFromOptions(final List<DecisionMakerOption> options) {
        return new BaseDecisionBehavior(
                options.stream().map(DecisionMakerFactory::createDecisionMaker).collect(Collectors.toSet()));
    }
}
