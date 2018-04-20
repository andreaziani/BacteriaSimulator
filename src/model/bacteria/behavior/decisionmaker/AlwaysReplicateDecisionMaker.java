package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

class AlwaysReplicateDecisionMaker extends AbstractReplicationDecisionMaker {

    @Override
    protected Map<Action, Double> decide(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        result.put(new SimpleAction(ActionType.REPLICATE), 1.0);
        return result;
    }
}
