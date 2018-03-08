package model.bacteria.behavior;

import model.action.ActionType;
import model.action.SimpleAction;

/**
 * A behavior that always propose to reproduce.
 */
public class AiwaysReproduceDecisionBehavior extends DecisionBehaviorDecorator implements ReproductionBehavior {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     */
    public AiwaysReproduceDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void updateDecisions() {
        super.updateDecisions();
        this.getDecisions().put(new SimpleAction(ActionType.REPLICATE), 1.0);
    }
}
