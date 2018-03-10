package model.bacteria.behavior;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

/**
 * Abstract implementation of a Behavior that evaluates some actions, assign a
 * score to each of them and choose the one with the best score.
 */
public abstract class AbstractDecisionBehavior implements Behavior {

    private final Map<ActionType, DecisionMaker> decisionStrategies;

    /**
     * Create an abstractDecisionBehavior.
     * 
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     */
    public AbstractDecisionBehavior(final Map<ActionType, DecisionMaker> decisionStrategies) {
        this.decisionStrategies = decisionStrategies;
    }

    /**
     * Modify the decisions already taken to adjust them accordingly to this
     * behavior preferences.
     * 
     * @param decisions
     *            the collection of decisions taken until now.
     */
    protected abstract void updateDecisions(Map<Action, Double> decisions);

    @Override
    public final Action chooseAction(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> decisions = this.decisionStrategies.entrySet().stream()
                                        .flatMap(x -> x.getValue()
                                                       .getDecision(knowledge)
                                                       .entrySet()
                                                       .stream()
                                                       .filter(k -> k.getKey().getType().equals(x.getKey())))
                                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, 
                                                (v1, v2) -> Math.max(v1, v2)));
        updateDecisions(decisions);
        return decisions.keySet().stream()
                                 .max((a1, a2) -> (int) (decisions.get(a1) - decisions.get(a2)))
                                 .orElseGet(() -> new SimpleAction(ActionType.NOTHING));
    }
}
