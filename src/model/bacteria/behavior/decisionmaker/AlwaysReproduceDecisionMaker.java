package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

/**
 * A DecisionMaker that always propose to reproduce.
 */
public class AlwaysReproduceDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        result.put(new SimpleAction(ActionType.REPLICATE), 1.0);
        return result;
    }
}
