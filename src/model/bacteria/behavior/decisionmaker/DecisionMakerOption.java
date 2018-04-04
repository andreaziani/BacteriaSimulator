package model.bacteria.behavior.decisionmaker;

import model.action.ActionType;

/**
 * enumeration representing all types of DecisionMaker a behavior can have. Each
 * correlated with the action it will decide.
 */
public enum DecisionMakerOption {
    /**
     * Represents a void decision maker about eat action.
     */
    NO_EATING(ActionType.EAT, "Never eats"),
    /**
     * Represents an AlwaysEatDecisionMaker.
     */
    ALWAYS_EAT(ActionType.EAT, "Eats all food"),
    /**
     * Represents a PreferentialEatingDecisionMaker.
     */
    PREFERENTIAL_EATING(ActionType.EAT, "Eats only foods he can use at that moment"),
    /**
     * Represents a void decision maker about move action.
     */
    NO_MOVEMENT(ActionType.MOVE, "Never moves"),
    /**
     * Represents a RandomMovementDecisionMaker.
     */
    RANDOM_MOVEMENT(ActionType.MOVE, "Moves randomly"),
    /**
     * Represents a PreferentialMovementDecisionMaker.
     */
    NEAR_FOOD_MOVEMENT(ActionType.MOVE, "Moves in the direction of the nearest food"),
    /**
     * Represents a void decision maker about replicate action.
     */
    NO_REPLICATION(ActionType.REPLICATE, "Never replicates"),
    /**
     * Represents an AlwaysReproduceDecisionMaker.
     */
    ALWAYS_REPLICATE(ActionType.REPLICATE, "Always tries to replicate"),
    /**
     * Represents a RandomReproductionDecisionMaker.
     */
    RANDOM_REPLICATION(ActionType.REPLICATE, "Replicates at random moments");

    private final ActionType type;
    private final String description;

    DecisionMakerOption(final ActionType type, final String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * @return the ActionType this DecisionMaker will make decisions about.
     */
    public ActionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return description;
    }
}
