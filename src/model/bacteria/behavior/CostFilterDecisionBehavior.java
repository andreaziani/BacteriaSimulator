package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * A behavior that filters all decisions taken and keeps only the ones the
 * bacteria can execute.
 */
public class CostFilterDecisionBehavior extends DecisionBehaviorDecorator {
    /**
     * Construct a new NearFoodMovementDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior.
     *
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     */
    public CostFilterDecisionBehavior(final AbstractDecisionBehavior delegate,
            final Map<ActionType, DecisionMaker> decisionStrategies) {
        super(delegate, decisionStrategies);
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        super.updateDecisions(decisions, knowledge);
        this.cleanActionDecisions(a -> knowledge.getActionCost(a).compareTo(knowledge.getBacteriaEnergy()) < 0,
                decisions);
    }
}
