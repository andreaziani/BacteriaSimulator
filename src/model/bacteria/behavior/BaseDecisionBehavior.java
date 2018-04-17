package model.bacteria.behavior;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * Base implementation of AbstractDecisionBehavior that does not change the
 * decisions taken by its strategies.
 */
public final class BaseDecisionBehavior extends AbstractDecisionBehavior {

    private final Set<DecisionMaker> decisionStrategies;

    /**
     * Create a BaseDecisionBehavior.
     * 
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     * @throws IllegalArgumentException
     *             if decisionStrategies is null.
     */
    public BaseDecisionBehavior(final Set<DecisionMaker> decisionStrategies) {
        super();
        if (decisionStrategies == null) {
            throw new IllegalArgumentException();
        }
        this.decisionStrategies = decisionStrategies;
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        decisions.putAll(this.decisionStrategies.stream().flatMap(x -> x.getDecision(knowledge).entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> Math.max(v1, v2))));
    }

    @Override
    public int hashCode() {
        return Objects.hash(decisionStrategies);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseDecisionBehavior other = (BaseDecisionBehavior) obj;
        return this.decisionStrategies.containsAll(other.decisionStrategies)
                && other.decisionStrategies.containsAll(this.decisionStrategies);
    }

}
