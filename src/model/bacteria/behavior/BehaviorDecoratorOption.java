package model.bacteria.behavior;

/**
 * enumeration representing all types of decorators a behavior can have.
 */
public enum BehaviorDecoratorOption {
    /**
     * Represents a CostFilterDecisionBehavior.
     */
    COST_FILTER,
    /**
     * Represents a PreferentialDecisionBehavior relative to the ActionType MOVE.
     */
    PREFERENTIAL_MOVE,
    /**
     * Represents a PreferentialDecisionBehavior relative to the ActionType EAT.
     */
    PREFERENTIAL_EAT,
    /**
     * Represents a PreferentialDecisionBehavior relative to the ActionType
     * REPLICATE.
     */
    PREFERENTIAL_REPLICATE;
}
