package model.bacteria.behavior;

import java.util.Random;

import model.Direction;
import model.action.ActionType;
import model.action.DirectionalActionImpl;

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
    protected void updateDecisions() {
        final Random rand = new Random();
        this.getDecisions().forEach((a, b) -> {
            if (a.getType() == ActionType.MOVE) {
                getDecisions().put(a, 0.0);
            }
        });
        this.getDecisions().put(
                new DirectionalActionImpl(ActionType.MOVE, Direction.values()[rand.nextInt(Direction.values().length)]),
                1.0);
        super.updateDecisions();
    }

}
