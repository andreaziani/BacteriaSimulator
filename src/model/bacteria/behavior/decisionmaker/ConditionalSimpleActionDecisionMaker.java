package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

abstract class ConditionalSimpleActionDecisionMaker implements DecisionMaker {

    private Action simpleAction;

    ConditionalSimpleActionDecisionMaker(final ActionType simpleActionType) {
        this.simpleAction = new SimpleAction(simpleActionType);
    }

    protected abstract boolean isGoodDecision(BacteriaKnowledge knowledge);

    @Override
    public final Map<Action, Double> getDecision(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> result = new HashMap<>();
        if (isGoodDecision(knowledge)) {
            result.put(simpleAction, 1.0);
        }
        return result;
    }
}
