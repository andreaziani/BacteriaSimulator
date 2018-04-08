package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import model.Direction;
import model.action.ActionType;
import model.action.DirectionalActionImpl;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.AbstractDecisionBehavior;
import model.bacteria.behavior.CostFilterDecisionBehavior;
import model.bacteria.behavior.PreferentialDecisionBehavior;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.perception.PerceptionImpl;

/**
 * Unit test for the Behavior of a Bacteria.
 */
public class TestBehavior {

    /**
     * Test DecisionMakers.
     */
    @Test
    public void testDecisionMakers() {
        final BacteriaKnowledge knowledge = new BacteriaKnowledge(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), TestUtils.bestDirection(Direction.NORTH)),
                TestUtils.allNutrientsBad(), x -> TestUtils.getSmallEnergy(), () -> TestUtils.getSmallEnergy());
        AbstractDecisionBehavior behavior = TestUtils.baseBehaviorFromOptions(Collections.emptyList());
        assertEquals(ActionType.NOTHING, behavior.chooseAction(knowledge).getType());
        behavior = TestUtils.baseBehaviorFromOptions(
                Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING, DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals(new DirectionalActionImpl(ActionType.MOVE, Direction.NORTH), behavior.chooseAction(knowledge));
        behavior = TestUtils.baseBehaviorFromOptions(
                Arrays.asList(DecisionMakerOption.ALWAYS_EAT, DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals(ActionType.EAT, behavior.chooseAction(knowledge).getType());
    }

    /**
     * Test the CostFilterDecisionBehavior.
     */
    @Test
    public void testCostFilterBehavior() {
        final BacteriaKnowledge knowledge = new BacteriaKnowledge(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), TestUtils.bestDirection(Direction.NORTH)),
                TestUtils.allNutrientGood(), TestUtils.singleLowCostActionType(ActionType.MOVE),
                () -> TestUtils.getSmallEnergy());
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.ALWAYS_EAT,
                        DecisionMakerOption.NEAR_FOOD_MOVEMENT, DecisionMakerOption.ALWAYS_REPLICATE));
        assertNotEquals(ActionType.MOVE, behavior.chooseAction(knowledge).getType());
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals(ActionType.MOVE, behavior.chooseAction(knowledge).getType());
    }

    /**
     * Test the PreferentialDecisionBehavior.
     */
    @Test
    public void testPreferentialBehavior() {
        final BacteriaKnowledge knowledge = new BacteriaKnowledge(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), TestUtils.bestDirection(Direction.NORTH)),
                TestUtils.allNutrientGood(), TestUtils.singleLargeCostActionType(ActionType.REPLICATE),
                () -> TestUtils.getSmallEnergy());
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING,
                        DecisionMakerOption.NEAR_FOOD_MOVEMENT, DecisionMakerOption.ALWAYS_REPLICATE));
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals(ActionType.EAT, behavior.chooseAction(knowledge).getType());
        behavior = new PreferentialDecisionBehavior(behavior, ActionType.MOVE);
        assertEquals(ActionType.MOVE, behavior.chooseAction(knowledge).getType());
    }
}
