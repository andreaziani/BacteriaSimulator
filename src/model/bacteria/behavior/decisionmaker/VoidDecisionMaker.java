package model.bacteria.behavior.decisionmaker;

import java.util.Collections;
import java.util.Map;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;

class VoidDecisionMaker implements DecisionMaker {

    @Override
    public Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        return Collections.emptyMap();
    }
}
