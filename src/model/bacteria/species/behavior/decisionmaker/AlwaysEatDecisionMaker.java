package model.bacteria.species.behavior.decisionmaker;

import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

class AlwaysEatDecisionMaker extends ConditionalSimpleActionDecisionMaker {

    AlwaysEatDecisionMaker() {
        super(ActionType.EAT);
    }

    @Override
    protected boolean isGoodDecision(final BacteriaKnowledge knowledge) {
        return knowledge.getCurrentPerception().getFood().isPresent();
    }

}
