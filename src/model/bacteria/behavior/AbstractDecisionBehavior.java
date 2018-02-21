package model.bacteria.behavior;

import java.util.HashSet;
import java.util.Set;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.perception.Perception;

/**
 * Abstract implementation of a Behavior that evaluates some actions, assign a
 * score to each of them and choose the one with the best score.
 */
public abstract class AbstractDecisionBehavior implements Behavior {

    private final Set<Decision> decisionSet;

    /**
     * Create an abstractDecisionBehavior.
     */
    public AbstractDecisionBehavior() {
        this.decisionSet = new HashSet<>();
    }

    /**
     * @return the decision set. This is not a copy or an unmodifiable set, it is
     *         intended to be modified.
     */
    protected final Set<Decision> getDecisionSet() {
        return decisionSet;
    }

    /**
     * This method must be used internally to modify the decision set, which can be
     * accessed using getDecisionSet. This method is the only way for an extension
     * of this class to make a decision about the Action to choose.
     */
    protected abstract void updateDecisionSet();

    @Override
    public final Action chooseAction(final Perception perception) {
        decisionSet.clear();
        updateDecisionSet();
        return decisionSet.stream().max((d1, d2) -> (int) (d1.getConfidence() - d2.getConfidence()))
                .map(x -> x.getAction()).orElseGet(() -> new SimpleAction(ActionType.NOTHING));
    }
}
