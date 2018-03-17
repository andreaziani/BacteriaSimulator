package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

class PreferentialEatingDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        if (knowledge.getCurrentPerception().getFood().isPresent()) {
            result.put(new SimpleAction(ActionType.EAT),
                    knowledge.getCurrentPerception().getFood().get().getNutrients().stream()
                            .mapToDouble(n -> knowledge.getNutrientEnergy(n)
                                    .multiply(knowledge.getCurrentPerception().getFood().get().getQuantityFromNutrient(n))
                                    .getAmount())
                            .sum() > 0 ? 1.0 : 0);
        }
        return result;
    }
}
