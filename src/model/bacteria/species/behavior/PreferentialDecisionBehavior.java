package model.bacteria.species.behavior;

import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.bacteria.BacteriaKnowledge;

/**
 * A behavior that set a preference for a particular ActionType if it is
 * available. A preference corresponds to an increment of 1 of the score, which
 * means that only other preferred actions can compete compete with an action
 * incremented by this class.
 */
public final class PreferentialDecisionBehavior extends DecisionBehaviorDecorator {

    private final ActionType preferred;

    /**
     * Construct a new PreferentialDecisionBehavior by taking a delegate. It
     * implements the decorator pattern.
     * 
     * @param delegate
     *            a Behavior that will make decisions and choose an action using
     *            this object's decisions.
     * @param preferredType
     *            an ActionType that this behavior will prefer, giving it more
     *            priority than the others.
     */
    public PreferentialDecisionBehavior(final AbstractDecisionBehavior delegate, final ActionType preferredType) {
        super(delegate);
        this.preferred = preferredType;
    }

    @Override
    protected void addDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        decisions.keySet().stream().filter(a -> a.getType().equals(preferred))
                .forEach(k -> decisions.put(k, decisions.get(k) + 1));
    }
}
