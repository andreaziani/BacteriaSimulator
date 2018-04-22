package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

import model.Direction;
import model.Energy;
import model.action.Action;
import model.action.ActionFactory;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.species.behavior.AbstractDecisionBehavior;
import model.bacteria.species.behavior.CostFilterDecisionBehavior;
import model.bacteria.species.behavior.ExplorerDecisionBehavior;
import model.bacteria.species.behavior.PreferentialDecisionBehavior;
import model.bacteria.species.behavior.decisionmaker.DecisionMakerOption;
import model.food.Nutrient;
import model.perception.PerceptionImpl;

/**
 * Unit test for the Behavior of a Bacteria.
 */
public class TestBehavior {

    private class KnowledgeBuilder {
        private Function<Nutrient, Energy> nutrientToEnergyConverter = TestUtils.allNutrientGood();
        private Function<Action, Energy> actionCostFunction = x -> TestUtils.getSmallEnergy();
        private final Supplier<Energy> bacteriaEnergy = () -> TestUtils.getSmallEnergy();
        private final Supplier<Double> bacteriaSpeed = () -> 0.0;
        private Map<Direction, Double> perceptionDirections = Collections.emptyMap();

        private KnowledgeBuilder setNutrientsBad() {
            nutrientToEnergyConverter = TestUtils.allNutrientsBad();
            return this;
        }

        private KnowledgeBuilder setActionCostFunction(final Function<Action, Energy> function) {
            actionCostFunction = function;
            return this;
        }

        private KnowledgeBuilder setPerceptionDirections(final Map<Direction, Double> perceptionDirections) {
            this.perceptionDirections = perceptionDirections;
            return this;
        }

        private BacteriaKnowledge build() {
            final BacteriaKnowledge result = new BacteriaKnowledge(
                    new PerceptionImpl(Optional.of(TestUtils.getAFood()), perceptionDirections),
                    nutrientToEnergyConverter, actionCostFunction, bacteriaEnergy, bacteriaSpeed);
            return result;
        }
    }

    /**
     * Test DecisionMakers.
     */
    @Test
    public void testDecisionMakers() {
        final BacteriaKnowledge knowledge = new KnowledgeBuilder()
                .setPerceptionDirections(TestUtils.bestDirection(Direction.NORTH)).setNutrientsBad().build();
        AbstractDecisionBehavior behavior = TestUtils.baseBehaviorFromOptions(Collections.emptyList());
        assertEquals("Behavior should choose to do nothing", ActionType.NOTHING,
                behavior.chooseAction(knowledge).getType());
        behavior = TestUtils.baseBehaviorFromOptions(
                Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING, DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals(
                "Behavior should choose to move north with the distance equals to that of the distance to the food",
                ActionFactory.createAction(ActionType.MOVE, Direction.NORTH,
                        knowledge.getCurrentPerception().distFromFood(Direction.NORTH).get()),
                behavior.chooseAction(knowledge));
        behavior = TestUtils.baseBehaviorFromOptions(
                Arrays.asList(DecisionMakerOption.ALWAYS_EAT, DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals("Behavior should choose to do eat", ActionType.EAT, behavior.chooseAction(knowledge).getType());

        behavior = TestUtils.baseBehaviorFromOptions(Collections.singletonList(DecisionMakerOption.ALWAYS_REPLICATE));
        assertEquals("Behavior should choose to replicate", ActionType.REPLICATE,
                behavior.chooseAction(knowledge).getType());
        knowledge.setReplicatingState(true);
        assertNotEquals("Behavior should not choose to replicate", ActionType.REPLICATE,
                behavior.chooseAction(knowledge).getType());
    }

    /**
     * Test the CostFilterDecisionBehavior.
     */
    @Test
    public void testCostFilterBehavior() {
        final BacteriaKnowledge knowledge = new KnowledgeBuilder()
                .setPerceptionDirections(TestUtils.bestDirection(Direction.NORTH))
                .setActionCostFunction(TestUtils.singleLowCostActionType(ActionType.MOVE)).build();
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.ALWAYS_EAT,
                        DecisionMakerOption.NEAR_FOOD_MOVEMENT, DecisionMakerOption.ALWAYS_REPLICATE));
        assertNotEquals("Behavior should not choose MOVE because other options have more value", ActionType.MOVE,
                behavior.chooseAction(knowledge).getType());
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals("Behavior should choose MOVE because other options must be filtered out", ActionType.MOVE,
                behavior.chooseAction(knowledge).getType());
    }

    /**
     * Test the PreferentialDecisionBehavior.
     */
    @Test
    public void testPreferentialBehavior() {
        final BacteriaKnowledge knowledge = new KnowledgeBuilder()
                .setPerceptionDirections(TestUtils.bestDirection(Direction.NORTH))
                .setActionCostFunction(TestUtils.singleLargeCostActionType(ActionType.REPLICATE)).build();
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING,
                        DecisionMakerOption.NEAR_FOOD_MOVEMENT, DecisionMakerOption.ALWAYS_REPLICATE));
        behavior = new CostFilterDecisionBehavior(behavior);
        assertEquals("Behavior should choose EAT because has more vaule", ActionType.EAT,
                behavior.chooseAction(knowledge).getType());
        behavior = new PreferentialDecisionBehavior(behavior, ActionType.MOVE);
        assertEquals("Behavior should choose MOVE because has more vaule with the preference", ActionType.MOVE,
                behavior.chooseAction(knowledge).getType());
    }

    /**
     * Test the ExplorerDecisionBehavior.
     */
    @Test
    public void testExplorer() {
        final BacteriaKnowledge knowledge = new KnowledgeBuilder()
                .setActionCostFunction(TestUtils.singleLargeCostActionType(ActionType.REPLICATE)).build();
        AbstractDecisionBehavior behavior = TestUtils
                .baseBehaviorFromOptions(Collections.singletonList(DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals("Behavior should choose NOTHING because it can't see foods", ActionType.NOTHING,
                behavior.chooseAction(knowledge).getType());
        behavior = new ExplorerDecisionBehavior(behavior);
        assertEquals("Behavior should choose MOVE because the explorer will decide so", ActionType.MOVE,
                behavior.chooseAction(knowledge).getType());

        behavior = TestUtils.baseBehaviorFromOptions(
                Arrays.asList(DecisionMakerOption.PREFERENTIAL_EATING, DecisionMakerOption.NEAR_FOOD_MOVEMENT));
        assertEquals("Behavior should choose EAT because it can't see foods in other direction and can eat",
                ActionType.EAT, behavior.chooseAction(knowledge).getType());
        behavior = new PreferentialDecisionBehavior(new ExplorerDecisionBehavior(behavior), ActionType.MOVE);
        assertEquals("Behavior should choose EAT because it prefers moving and will want to explore", ActionType.MOVE,
                behavior.chooseAction(knowledge).getType());

    }
}
