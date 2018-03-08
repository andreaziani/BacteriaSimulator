package model.bacteria.behavior;

import java.util.EnumMap;
import java.util.Map;

import model.Direction;
import model.action.ActionType;
import model.action.DirectionalActionImpl;

/**
 * A behavior for choosing how to move that assigns to each move direction a
 * preference based on the percepted distance from the nearest foods.
 */
public class NearFoodMovementDecisionBehavior extends DecisionBehaviorDecorator implements MovementBehavior {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     */
    public NearFoodMovementDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void updateDecisions() {
        super.updateDecisions();
        cleanActionDecisions(a -> a.getType() == ActionType.MOVE);
        final Map<Direction, Double> values = new EnumMap<>(Direction.class);
        Double tempVal = 0.0;
        for (final Direction d : Direction.values()) {
            final Double dirVal = this.getCurrentPerception().distFromFood(d).orElse(0.0);
            values.put(d, dirVal);
            tempVal += dirVal;
        }
        final double maxVal = tempVal;
        values.forEach((d, v) -> this.getDecisions().put(new DirectionalActionImpl(ActionType.MOVE, d), v / maxVal));
    }
}
