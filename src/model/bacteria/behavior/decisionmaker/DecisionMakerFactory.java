package model.bacteria.behavior.decisionmaker;

/**
 * Abstract factory of decision makers that takes a DecisionMakerOption as an
 * input and gives a DecisionMaker as output.
 */
public final class DecisionMakerFactory {

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
        DecisionMaker result = null;
        switch (option) {
        case ALWAYS_EAT:
            result = new AlwaysEatDecisionMaker();
            break;
        case ALWAYS_REPLICATE:
            result = new AlwaysReplicateDecisionMaker();
            break;
        case NEAR_FOOD_MOVEMENT:
            result = new NearFoodMovementDecisionMaker();
            break;
        case PREFERENTIAL_EATING:
            result = new PreferentialEatingDecisionMaker();
            break;
        case RANDOM_MOVEMENT:
            result = new RandomMovementDecisionMaker();
            break;
        case RANDOM_REPLICATION:
            result = new RandomReplicationDecisionMaker();
            break;
        default:
            break;
        }
        return result;
    }

}
