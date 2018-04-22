package model.bacteria.species.behavior.decisionmaker;

import java.util.EnumMap;
import java.util.Map;

/**
 * Static factory of decision makers that takes a DecisionMakerOption as an
 * input and gives a DecisionMaker as output.
 */
public final class DecisionMakerFactory {

    private static Map<DecisionMakerOption, DecisionMaker> instances = new EnumMap<>(DecisionMakerOption.class);

    static {
        instances.put(DecisionMakerOption.ALWAYS_EAT, new AlwaysEatDecisionMaker());
        instances.put(DecisionMakerOption.PREFERENTIAL_EATING, new PreferentialEatingDecisionMaker());
        instances.put(DecisionMakerOption.RANDOM_MOVEMENT, new RandomMovementDecisionMaker());
        instances.put(DecisionMakerOption.NEAR_FOOD_MOVEMENT, new NearFoodMovementDecisionMaker());
        instances.put(DecisionMakerOption.ALWAYS_REPLICATE, new AlwaysReplicateDecisionMaker());
        instances.put(DecisionMakerOption.RANDOM_REPLICATION, new RandomReplicationDecisionMaker());
        final VoidDecisionMaker voidDecisionMaker = new VoidDecisionMaker();
        instances.put(DecisionMakerOption.NO_EATING, voidDecisionMaker);
        instances.put(DecisionMakerOption.NO_MOVEMENT, voidDecisionMaker);
        instances.put(DecisionMakerOption.NO_REPLICATION, voidDecisionMaker);
    }
    private DecisionMakerFactory() {
    }

    /**
     * Create a new DecisionMaker by taking the type of the decisionMaker as an enum
     * associated to it.
     * 
     * @param option
     *            the option corresponding to the type of DecisionMaker to create.
     * @return a new DecisionMaker of the type indicated by option.
     */
    public static DecisionMaker createDecisionMaker(final DecisionMakerOption option) {
        return instances.get(option);
    }
}
