package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * Abstract implementation of Behavior that follows the decorator pattern. The
 * method updateDecisions is delegated to the Behavior taken by the constructor.
 */
public abstract class DecisionBehaviorDecorator extends AbstractDecisionBehavior {

    private final AbstractDecisionBehavior delegate;

    /**
     * Construct a new DecisionBehaviorDecorator by taking a delegate.
     * 
     * @param delegate
     *            a Behavior.
     *
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     */
    public DecisionBehaviorDecorator(final AbstractDecisionBehavior delegate,
            final Map<ActionType, DecisionMaker> decisionStrategies) {
        super(decisionStrategies);
        this.delegate = delegate;
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        this.delegate.updateDecisions(decisions, knowledge);
    }
}
