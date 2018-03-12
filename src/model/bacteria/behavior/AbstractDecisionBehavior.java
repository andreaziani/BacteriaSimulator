package model.bacteria.behavior;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * Abstract implementation of a Behavior that evaluates some actions, assign a
 * score to each of them and choose the one with the best score.
 */
public abstract class AbstractDecisionBehavior implements Behavior {

    private final Map<ActionType, DecisionMaker> decisionStrategies;

    /**
     * Create an AbstractDecisionBehavior.
     * 
     * @param decisionStrategies
     *            the strategies this Behavior will use to make decisions about each
     *            ActionType.
     */
    public AbstractDecisionBehavior(final Map<ActionType, DecisionMaker> decisionStrategies) {
        this.decisionStrategies = decisionStrategies;
    }

    /**
     * Search the decisions of the behavior and set to zero the value of all actions
     * that satisfy the Predicate.
     * 
     * @param cond
     *            a condition for each Action.
     * @param decisions
     *            the collection of decisions taken until now.
     */
    protected void cleanActionDecisions(final Predicate<Action> cond, final Map<Action, Double> decisions) {
        decisions.forEach((a, b) -> {
            if (cond.test(a)) {
                decisions.put(a, 0.0);
            }
        });
    }
    
    /**
     * Modify the decisions already taken to adjust them accordingly to this
     * behavior preferences.
     * 
     * @param decisions
     *            the collection of decisions taken until now.
     */
    protected abstract void updateDecisions(Map<Action, Double> decisions, BacteriaKnowledge knowledge);

    @Override
    public Action chooseAction(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> decisions = this.decisionStrategies.entrySet().stream()
                                        .flatMap(x -> x.getValue()
                                                       .getDecision(knowledge)
                                                       .entrySet()
                                                       .stream()
                                                       .filter(k -> k.getKey().getType().equals(x.getKey())))
                                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, 
                                                (v1, v2) -> Math.max(v1, v2)));
        updateDecisions(decisions, knowledge);
        return decisions.keySet().stream()
                                 .max((a1, a2) -> (int) (decisions.get(a1) - decisions.get(a2)))
                                 .orElseGet(() -> new SimpleAction(ActionType.NOTHING));
    }
}
