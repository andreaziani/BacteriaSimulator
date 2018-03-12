package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

/**
 * A behavior that set a preference for a particular ActionType if it is
 * available.
 */
public class PreferentialDecisionBehavior extends DecisionBehaviorDecorator {

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
        this.preferred = preferredType;
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        super.updateDecisions(decisions, knowledge);
        if (decisions.keySet().stream().anyMatch(a -> a.getType().equals(preferred))) {
            this.cleanActionDecisions(a -> !a.getType().equals(preferred), decisions);
        }
    }
}
