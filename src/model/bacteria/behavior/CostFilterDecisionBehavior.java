package model.bacteria.behavior;

/**
 * A behavior that filters all decisions taken and keeps only the ones the
 * bacteria can execute.
 */
public class CostFilterDecisionBehavior extends DecisionBehaviorDecorator implements WrapperBehavior {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     */
    public CostFilterDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void updateDecisions() {
        super.updateDecisions();
        this.cleanActionDecisions(a -> this.getActionCost(a).compareTo(this.getBacteriaEnergy()) < 0);
    }
}
