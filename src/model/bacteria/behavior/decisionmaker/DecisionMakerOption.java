package model.bacteria.behavior.decisionmaker;

import model.action.ActionType;

/**
 * enumeration representing all types of DecisionMaker a behavior can have. Each
 * correlated with the action it will decide.
 */
public enum DecisionMakerOption {
    /**
     * Represents an AlwaysEatDecisionMaker.
     */
    ALWAYS_EAT(ActionType.EAT),
    /**
     * Represents a PreferentialEatingDecisionMaker.
     */
    PREFERENTIAL_EATING(ActionType.EAT),
    /**
     * Represents a RandomMovementDecisionMaker.
     */
    RANDOM_MOVEMENT(ActionType.MOVE),
    /**
     * Represents a PreferentialMovementDecisionMaker.
     */
    PREFERENTIAL_MOVEMENT(ActionType.MOVE),
    /**
     * Represents an AlwaysReproduceDecisionMaker.
     */
    ALWAYS_REPLICATE(ActionType.REPLICATE),
    /**
     * Represents a RandomReproductionDecisionMaker.
     */
    RANDOM_REPLICATE(ActionType.REPLICATE);

    private final ActionType type;

    DecisionMakerOption(final ActionType type) {
        this.type = type;
    }

    /**
     * @return the ActionType this DecisionMaker will make decisions about.
     */
    public ActionType getType() {
        return type;
    }

}
