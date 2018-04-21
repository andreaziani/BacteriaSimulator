package model.bacteria.behavior.decisionmaker;

import java.util.Random;

import model.bacteria.BacteriaKnowledge;

class RandomReplicationDecisionMaker extends AlwaysReplicateDecisionMaker {
    private final Random rand = new Random();

    @Override
    protected boolean isGoodDecision(final BacteriaKnowledge knowledge) {
        return super.isGoodDecision(knowledge) && rand.nextBoolean();
    }
}
