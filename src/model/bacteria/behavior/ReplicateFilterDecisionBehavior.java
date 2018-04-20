package model.bacteria.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

final class ReplicateFilterDecisionBehavior extends DecisionBehaviorDecorator {

    ReplicateFilterDecisionBehavior(final AbstractDecisionBehavior delegate) {
        super(delegate);
    }

    @Override
    protected void addDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        if (knowledge.isReplicating()) {
            decisions.keySet().stream().filter(a -> a.getType().equals(ActionType.REPLICATE))
                    .forEach(a -> decisions.put(a, 0.0));
        }
    }
}
