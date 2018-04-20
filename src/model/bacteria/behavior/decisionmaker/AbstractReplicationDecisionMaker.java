package model.bacteria.behavior.decisionmaker;

import java.util.Collections;
import java.util.Map;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;

abstract class AbstractReplicationDecisionMaker implements DecisionMaker {

    protected abstract Map<Action, Double> decide(BacteriaKnowledge knowledge);

    @Override
    public final Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        if (knowledge.isReplicating()) {
            return decide(knowledge);
        }
        return Collections.emptyMap();
    }
}
