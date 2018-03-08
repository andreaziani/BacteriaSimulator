package model.bacteria.behavior;

import model.action.ActionType;

/**
 * A behavior that set a preference for a particular ActionType if it is
 * available.
 */
public class PreferentialDecisionBehavior extends DecisionBehaviorDecorator implements WrapperBehavior {

    private final ActionType preferred;

    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     * @param preferredType
     *            an ActionType that this behavior will prefer, giving it more
     *            priority than the others.
     */
    public PreferentialDecisionBehavior(final AbstractDecisionBehavior delegate, final ActionType preferredType) {
        super(delegate);
        preferred = preferredType;
    }

    @Override
    protected void updateDecisions() {
        super.updateDecisions();
        if (this.getDecisions().keySet().stream().anyMatch(a -> a.getType().equals(preferred))) {
            this.cleanActionDecisions(a -> !a.getType().equals(preferred));
        }
    }
}
