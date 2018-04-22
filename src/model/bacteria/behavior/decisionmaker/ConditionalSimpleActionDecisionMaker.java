package model.bacteria.behavior.decisionmaker;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionFactory;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

abstract class ConditionalSimpleActionDecisionMaker implements DecisionMaker {

    private final Action simpleAction;

    ConditionalSimpleActionDecisionMaker(final ActionType simpleActionType) {
        this.simpleAction = ActionFactory.createAction(simpleActionType);
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
