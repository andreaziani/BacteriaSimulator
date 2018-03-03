package model.bacteria.behavior;

import model.action.ActionType;
import model.action.SimpleAction;

/**
 * A behavior for choosing if to eat that considers the nutrients composing the
 * Food where it stands before assigning a value to the action.
 */
public class PreferentialEatingDecisionBehavior extends DecisionBehaviorDecorator implements EatingBehavior {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     */
    public PreferentialEatingDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void updateDecisions() { //TODO
        cleanActionDecisions(a -> a.getType() == ActionType.EAT);
        if (this.getCurrentPerception().getFood().isPresent()) {
            this.getDecisions().put(new SimpleAction(ActionType.EAT), 1.0);
        }
        super.updateDecisions();
    }
}
