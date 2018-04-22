package model.bacteria.behavior.decisionmaker;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.action.Action;
import model.action.ActionFactory;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

class NearFoodMovementDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        final Map<Direction, Double> distances = new EnumMap<>(Direction.class);
        double tempVal = 0.0;
        for (final Direction d : Direction.values()) {
            final double dirVal = knowledge.getCurrentPerception().distFromFood(d).orElse(0.0);
            distances.put(d, dirVal);
            tempVal += dirVal;
        }
        final double totVal = tempVal;
        distances.forEach((d,
                v) -> result.put(ActionFactory.createAction(ActionType.MOVE, d,
                        Math.min(knowledge.getCurrentPerception().distFromFood(d).orElse(knowledge.getSpeed()),
                                knowledge.getSpeed())),
                        v != 0 ? ((totVal - v) / totVal) : 0));
        return result;
    }
}
