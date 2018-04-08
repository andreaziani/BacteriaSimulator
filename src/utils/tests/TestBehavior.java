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
import model.bacteria.behavior.ExplorerDecisionBehavior;
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
                TestUtils.allNutrientsBad(), x -> TestUtils.getSmallEnergy(), () -> TestUtils.getSmallEnergy(),
                () -> 0.0);
        AbstractDecisionBehavior behavior = TestUtils.baseBehaviorFromOptions(Collections.emptyList());
        assertEquals(ActionType.NOTHING, behavior.chooseAction(knowledge).getType());
        behavior = TestUtils.baseBehaviorFromOptions(
                Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING, DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals(
                new DirectionalActionImpl(ActionType.MOVE, Direction.NORTH,
                        knowledge.getCurrentPerception().distFromFood(Direction.NORTH).get()),
                behavior.chooseAction(knowledge));
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
                () -> TestUtils.getSmallEnergy(), () -> 0.0);
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
                () -> TestUtils.getSmallEnergy(), () -> 0.0);
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING,
                        DecisionMakerOption.NEAR_FOOD_MOVEMENT, DecisionMakerOption.ALWAYS_REPLICATE));
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals(ActionType.EAT, behavior.chooseAction(knowledge).getType());
        behavior = new PreferentialDecisionBehavior(behavior, ActionType.MOVE);
        assertEquals(ActionType.MOVE, behavior.chooseAction(knowledge).getType());
    }

    /**
     * Test the ExplorerDecisionBehavior.
     */
    @Test
    public void testExplorer() {
        final BacteriaKnowledge knowledge = new BacteriaKnowledge(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), Collections.emptyMap()),
                TestUtils.allNutrientGood(), TestUtils.singleLargeCostActionType(ActionType.REPLICATE),
                () -> TestUtils.getSmallEnergy(), () -> 0.0);
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals(ActionType.NOTHING, behavior.chooseAction(knowledge).getType());
        behavior = new ExplorerDecisionBehavior(behavior);
        assertEquals(ActionType.MOVE, behavior.chooseAction(knowledge).getType());

        behavior = new PreferentialDecisionBehavior(
                TestUtils.baseBehaviorFromOptions(
                        Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING, DecisionMakerOption.NEAR_FOOD_MOVEMENT)),
                ActionType.MOVE);
        assertEquals(ActionType.EAT, behavior.chooseAction(knowledge).getType());
        behavior = new ExplorerDecisionBehavior(behavior);
        assertEquals(ActionType.MOVE, behavior.chooseAction(knowledge).getType());

    }
}
