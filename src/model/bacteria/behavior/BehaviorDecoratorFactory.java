package model.bacteria.behavior;

import model.action.ActionType;

/**
 * Static factory of behavior decorators that takes a BehaviorDecoratorOption
 * as an input and gives a DecisionBehaviorDecorator as output.
 */
public final class BehaviorDecoratorFactory {

    private BehaviorDecoratorFactory() {
    }

    /**
     * Create a new DecisionBehavior decorator by taking the object to decorate as
     * input and the type of the decorator as an enum associated to it.
     * 
     * @param decoratorOption
     *            the option corresponding to the type of decorator to create.
     * @param behavior
     *            the Behavior to decorate.
     * @return a new DecisionBehavior of the type indicated by decoratorOption
     *         decorating behavior.
     */
    public static DecisionBehaviorDecorator createDecorator(final BehaviorDecoratorOption decoratorOption,
            final AbstractDecisionBehavior behavior) {
        DecisionBehaviorDecorator result = null;
        switch (decoratorOption) {
        case COST_FILTER:
            result = new CostFilterDecisionBehavior(behavior);
            break;
        case PREFERENTIAL_EAT:
            result = new PreferentialDecisionBehavior(behavior, ActionType.EAT);
            break;
        case PREFERENTIAL_MOVE:
            result = new PreferentialDecisionBehavior(behavior, ActionType.MOVE);
            break;
        case PREFERENTIAL_REPLICATE:
            result = new PreferentialDecisionBehavior(behavior, ActionType.REPLICATE);
            break;
        default:
            break;
        }
        return result;
    }

}
