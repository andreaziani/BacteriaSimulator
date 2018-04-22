package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Direction;
import model.action.Action;
import model.action.ActionFactory;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

class RandomMovementDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        final Random rand = new Random();
        for (final Direction d : Direction.values()) {
            result.put(ActionFactory.createAction(ActionType.MOVE, d, knowledge.getSpeed()), rand.nextDouble());
        }
        return result;
    }
}
