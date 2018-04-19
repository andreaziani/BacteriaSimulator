package model.bacteria.behavior;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

/**
 * Abstract implementation of a Behavior that evaluates some actions, assign a
 * score to each of them and choose the one with the best score.
 */
public abstract class AbstractDecisionBehavior implements Behavior {

    /**
     * Modify the decisions already taken to adjust them accordingly to this
     * behavior preferences.
     * 
     * @param decisions
     *            the collection of decisions taken until now.
     * @param knowledge
     *            the current knowledge of the bacteria making this decisions.
     */
    protected abstract void updateDecisions(Map<Action, Double> decisions, BacteriaKnowledge knowledge);

    @Override
    public final Action chooseAction(final BacteriaKnowledge knowledge) {
        final Map<Action, Double> decisions = new HashMap<>();
        updateDecisions(decisions, knowledge);
        return decisions.keySet().stream().filter(x -> decisions.get(x) > 0)
                .max((a1, a2) -> Double.compare(decisions.get(a1), decisions.get(a2)))
                .orElseGet(() -> new SimpleAction(ActionType.NOTHING));
    }
}
