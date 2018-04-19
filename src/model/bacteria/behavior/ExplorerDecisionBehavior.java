package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMakerFactory;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;

/**
 * A behavior that moves randomly if the bacteria has no food to move to.
 */
public final class ExplorerDecisionBehavior extends DecisionBehaviorDecorator {
    /**
     * Construct a new EplorerDecisionBehavior by taking a delegate. It implements
     * the decorator pattern.
     * 
     * @param delegate
     *            a Behavior.
     */
    public ExplorerDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void addDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        if (decisions.entrySet().stream().anyMatch(e -> e.getKey().getType().equals(ActionType.MOVE))
                && decisions.entrySet().stream().filter(e -> e.getKey().getType().equals(ActionType.MOVE))
                        .noneMatch(e -> e.getValue() > 0)) {
            decisions.putAll(DecisionMakerFactory.createDecisionMaker(DecisionMakerOption.RANDOM_MOVEMENT)
                    .getDecision(knowledge));
        }
    }
}
