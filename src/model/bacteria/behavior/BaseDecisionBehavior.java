package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * Base implementation of AbstractDecisionBehavior that does not change the
 * decisions taken by its strategies.
 */
public class BaseDecisionBehavior extends AbstractDecisionBehavior {
    /**
     * Create a BaseDecisionBehavior.
     * 
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     */
    public BaseDecisionBehavior(final Map<ActionType, DecisionMaker> decisionStrategies) {
        super(decisionStrategies);
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {

    }

}
