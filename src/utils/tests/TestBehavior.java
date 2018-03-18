package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import model.Direction;
import model.EnergyImpl;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.AbstractDecisionBehavior;
import model.bacteria.behavior.BaseDecisionBehavior;
import model.bacteria.behavior.CostFilterDecisionBehavior;
import model.bacteria.behavior.decisionmaker.DecisionMakerFactory;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import model.perception.PerceptionImpl;

/**
 * Unit test for the Behavior of a Bacteria.
 */
public class TestBehavior {
    private static final double V1 = 1;
    private static final double V2 = 2;
    private static final double V3 = 3;

    private BacteriaKnowledge goodFoodKnowledge;
    private AbstractDecisionBehavior behavior;

    /**
     * Initialize knowledge for the tests.
     */
    @Before
    public void initKnowledge() {
        final FoodFactory factory = new FoodFactoryImpl();
        final Map<Nutrient, Double> nutrients = new HashMap<>();
        nutrients.put(Nutrient.CARBOHYDRATES, V1);
        nutrients.put(Nutrient.HYDROLYSATES, V2);
        nutrients.put(Nutrient.PEPTONES, V3);
        final Map<Direction, Double> distances = new EnumMap<>(Direction.class);
        for (final Direction d : Direction.values()) {
            distances.put(d, (double) d.ordinal());
        }
        goodFoodKnowledge = new BacteriaKnowledge(
                new PerceptionImpl(Optional.of(factory.createFoodFromNutrients(nutrients)), distances),
                x -> new EnergyImpl(x.ordinal()), x -> new EnergyImpl(x.getType().ordinal()),
                new EnergyImpl(ActionType.EAT.ordinal()));
    }

    /**
     * Initialize behavior of the tests.
     */
    @Before
    public void initBehavior() {
        final Set<DecisionMakerOption> options = new HashSet<>();
        options.add(DecisionMakerOption.ALWAYS_EAT);
        options.add(DecisionMakerOption.ALWAYS_REPLICATE);
        options.add(DecisionMakerOption.RANDOM_MOVEMENT);
        behavior = new BaseDecisionBehavior(
                options.stream()
                       .map(DecisionMakerFactory::createDecisionMaker)
                       .collect(Collectors.toSet()));
    }

    /**
     * Test that the action given by a Behavior are as expected.
     */
    @Test
    public void testCorrectAction() {
        assertNotEquals(ActionType.EAT, behavior.chooseAction(goodFoodKnowledge).getType());
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals(ActionType.EAT, behavior.chooseAction(goodFoodKnowledge).getType());
    }
}
