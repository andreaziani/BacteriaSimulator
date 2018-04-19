package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;

/**
 * A behavior that filters all decisions taken and keeps only the ones the
 * bacteria can execute.
 */
public final class CostFilterDecisionBehavior extends DecisionBehaviorDecorator {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior.
     */
    public CostFilterDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void addDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        this.cleanActionDecisions(a -> knowledge.getActionCost(a).compareTo(knowledge.getBacteriaEnergy()) > 0,
                decisions);
    }
}
