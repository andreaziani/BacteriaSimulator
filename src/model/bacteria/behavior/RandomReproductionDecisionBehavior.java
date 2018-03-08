package model.bacteria.behavior;

import java.util.Random;

import model.action.ActionType;
import model.action.SimpleAction;

/**
 * A behavior for choosing if to reproduce randomly.
 */
public class RandomReproductionDecisionBehavior extends DecisionBehaviorDecorator implements ReproductionBehavior {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     */
    public RandomReproductionDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void updateDecisions() {
        super.updateDecisions();
        final Random rand = new Random();
        if (rand.nextBoolean()) {
            this.getDecisions().put(new SimpleAction(ActionType.REPLICATE), 1.0);
        }
    }
}
