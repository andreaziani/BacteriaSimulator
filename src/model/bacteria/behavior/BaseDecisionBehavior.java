package model.bacteria.behavior;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * Base implementation of AbstractDecisionBehavior that does not change the
 * decisions taken by its strategies.
 */
public class BaseDecisionBehavior extends AbstractDecisionBehavior {

    private final Set<DecisionMaker> decisionStrategies;

    /**
     * Create a BaseDecisionBehavior.
     * 
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     */
    public BaseDecisionBehavior(final Set<DecisionMaker> decisionStrategies) {
        super();
        this.decisionStrategies = decisionStrategies;
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        decisions.putAll(
                this.decisionStrategies.stream()
                                       .flatMap(x -> x.getDecision(knowledge)
                                                      .entrySet()
                                                      .stream())
                                       .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                               (v1, v2) -> Math.max(v1, v2))));
    }

}
