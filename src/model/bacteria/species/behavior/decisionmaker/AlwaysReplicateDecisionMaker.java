package model.bacteria.species.behavior.decisionmaker;

import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

class AlwaysReplicateDecisionMaker extends ConditionalSimpleActionDecisionMaker {

    AlwaysReplicateDecisionMaker() {
        super(ActionType.REPLICATE);
    }

    @Override
    protected boolean isGoodDecision(final BacteriaKnowledge knowledge) {
        return !knowledge.isReplicating();
    }

}
