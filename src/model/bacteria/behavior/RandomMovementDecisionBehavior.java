package model.bacteria.behavior;

import java.util.Random;

import model.Direction;
import model.action.ActionType;

/**
 * A behavior for choosing out to move that prefers going in a random direction.
 */
public class RandomMovementDecisionBehavior extends DecisionBehaviorDecorator implements MovementBehavior {
    /**
     * Construct a new RandomMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     */
    public RandomMovementDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void updateDecisionSet() {
        Random rand = new Random();
        this.getDecisionSet().add(DecisionFactory.directionalDecision(ActionType.MOVE,
                Direction.values()[rand.nextInt(Direction.values().length)], 1));
        super.updateDecisionSet();
    }

}
