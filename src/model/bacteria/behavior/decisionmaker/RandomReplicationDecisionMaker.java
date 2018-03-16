package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

class RandomReplicationDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        final Random rand = new Random();
        if (rand.nextBoolean()) {
            result.put(new SimpleAction(ActionType.REPLICATE), 1.0);
        }
        return result;
    }
}
