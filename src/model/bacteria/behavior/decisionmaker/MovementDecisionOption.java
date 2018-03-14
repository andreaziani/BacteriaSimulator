package model.bacteria.behavior.decisionmaker;
/**
 * enumeration representing all types of options for a DecisionMaker concerning the ActionType MOVE.
 */
public enum MovementDecisionOption {
    /**
     * Represents a RandomMovementDecisionMaker.
     */
    RANDOM_MOVEMENT,
    /**
     * Represents a PreferentialMovementDecisionMaker.
     */
    PREFERENTIAL_MOVEMENT;
}
