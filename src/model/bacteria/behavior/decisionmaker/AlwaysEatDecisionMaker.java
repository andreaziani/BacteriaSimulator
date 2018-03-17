package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

class AlwaysEatDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        if (knowledge.getCurrentPerception().getFood().isPresent()) {
            result.put(new SimpleAction(ActionType.EAT), 1.0);
        }
        return result;
    }
}
