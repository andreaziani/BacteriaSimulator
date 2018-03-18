package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import model.Direction;
import model.Energy;
import model.EnergyImpl;
import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.AbstractDecisionBehavior;
import model.bacteria.behavior.BaseDecisionBehavior;
import model.bacteria.behavior.CostFilterDecisionBehavior;
import model.bacteria.behavior.decisionmaker.DecisionMakerFactory;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import model.perception.PerceptionImpl;

/**
 * Unit test for the Behavior of a Bacteria.
 */
public class TestBehavior {
    private static final double SMALL_DOUBLE = 1;
    private static final double LARGE_DOUBLE = 10;

    private static final Energy SMALL_ENERGY = new EnergyImpl(SMALL_DOUBLE);
    private static final Energy LARGE_ENERGY = new EnergyImpl(LARGE_DOUBLE);

    private Food food1;
    private List<DecisionMakerOption> options;

    /**
     * Initialize fields used in the tests.
     */
    @Before
    public void init() {
        final FoodFactory factory = new FoodFactoryImpl();
        final Map<Nutrient, Double> nutrients = new HashMap<>();
        nutrients.put(Nutrient.CARBOHYDRATES, SMALL_DOUBLE);
        nutrients.put(Nutrient.HYDROLYSATES, LARGE_DOUBLE);
        food1 = factory.createFoodFromNutrients(nutrients);
        options = new ArrayList<>();
        options.add(DecisionMakerOption.ALWAYS_EAT);
        options.add(DecisionMakerOption.ALWAYS_REPLICATE);
        options.add(DecisionMakerOption.RANDOM_MOVEMENT);
    }

    private Function<Action, Energy> singleLowCostActionType(final ActionType type) {
        return x -> x.getType().equals(type) ? SMALL_ENERGY : LARGE_ENERGY;
    }

    private Map<Direction, Double> bestDirection(final Direction dir) {
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

    private Function<Nutrient, Energy> allNutrientGood() {
        return x -> SMALL_ENERGY;
    }

    private BaseDecisionBehavior baseBehaviorFromOptions(final List<DecisionMakerOption> options) {
        return new BaseDecisionBehavior(
                options.stream()
                .map(DecisionMakerFactory::createDecisionMaker)
                .collect(Collectors.toSet()));
    }

    /**
     * Test that the action given by a Behavior are as expected.
     */
    @Test
    public void testCorrectAction() {
        final BacteriaKnowledge knowledge = new BacteriaKnowledge(
                new PerceptionImpl(Optional.of(food1), bestDirection(Direction.NORTH)), 
                allNutrientGood(),
                singleLowCostActionType(ActionType.EAT), SMALL_ENERGY);
        AbstractDecisionBehavior behavior = baseBehaviorFromOptions(options);
        assertNotEquals(ActionType.EAT, behavior.chooseAction(knowledge).getType());
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals(ActionType.EAT, behavior.chooseAction(knowledge).getType());
    }
}
