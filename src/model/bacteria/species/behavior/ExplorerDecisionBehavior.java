package model.bacteria.species.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMakerFactory;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;

/**
 * A behavior that moves randomly if the bacteria has decided to move nowhere
 * else. It only works if the behavior has previously decided that it may choose
 * to move but no direction is good. This is tested by controlling that there is
 * at least a decision concerning movement and no decision with a score greater
 * than zero for movement.
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
