package model.bacteria.species.behavior.decisionmaker;

import model.bacteria.BacteriaKnowledge;

class PreferentialEatingDecisionMaker extends AlwaysEatDecisionMaker {

    @Override
    protected boolean isGoodDecision(final BacteriaKnowledge knowledge) {
        return super.isGoodDecision(knowledge)
                && knowledge.getCurrentPerception().getFood().get().getNutrients().stream()
                        .mapToDouble(n -> knowledge.getNutrientEnergy(n).getAmount())
                        .anyMatch(x -> x > 0);
    }
}
