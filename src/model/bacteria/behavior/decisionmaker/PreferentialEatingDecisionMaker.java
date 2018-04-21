package model.bacteria.behavior.decisionmaker;

import model.bacteria.BacteriaKnowledge;

class PreferentialEatingDecisionMaker extends AlwaysEatDecisionMaker {

    @Override
    protected boolean isGoodDecision(final BacteriaKnowledge knowledge) {
        return super.isGoodDecision(knowledge)
                && knowledge.getCurrentPerception().getFood().get().getNutrients().stream()
                        .mapToDouble(n -> knowledge.getNutrientEnergy(n)
                                .multiply(knowledge.getCurrentPerception().getFood().get().getQuantityFromNutrient(n))
                                .getAmount())
                        .sum() > 0;
    }
}
