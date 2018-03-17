package model.bacteria.behavior.decisionmaker;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalActionImpl;
import model.bacteria.BacteriaKnowledge;

class NearFoodMovementDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        final Map<Direction, Double> values = new EnumMap<>(Direction.class);
        Double tempVal = 0.0;
        for (final Direction d : Direction.values()) {
            final Double dirVal = knowledge.getCurrentPerception().distFromFood(d).orElse(0.0);
            values.put(d, dirVal);
            tempVal += dirVal;
        }
        final double maxVal = tempVal;
        values.forEach((d, v) -> result.put(new DirectionalActionImpl(ActionType.MOVE, d), v / maxVal));
        return result;
    }
}
