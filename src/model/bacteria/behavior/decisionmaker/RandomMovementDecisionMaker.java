package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Direction;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalActionImpl;
import model.bacteria.BacteriaKnowledge;

class RandomMovementDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        final Random rand = new Random();
        result.put(
                new DirectionalActionImpl(ActionType.MOVE, Direction.values()[rand.nextInt(Direction.values().length)]),
                1.0);
        return result;
    }
}
