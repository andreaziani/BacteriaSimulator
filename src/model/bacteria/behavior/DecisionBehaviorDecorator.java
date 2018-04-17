package model.bacteria.behavior;

import java.util.Map;
import java.util.Objects;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;

/**
 * Abstract implementation of Behavior that follows the decorator pattern. The
 * method updateDecisions is delegated to the Behavior taken by the constructor.
 */
public abstract class DecisionBehaviorDecorator extends AbstractDecisionBehavior {

    private final AbstractDecisionBehavior delegate;

    /**
     * Construct a new DecisionBehaviorDecorator by taking a delegate.
     * 
     * @param delegate
     *            a Behavior.
     */
    public DecisionBehaviorDecorator(final AbstractDecisionBehavior delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    protected void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        this.delegate.updateDecisions(decisions, knowledge);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(delegate);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DecisionBehaviorDecorator other = (DecisionBehaviorDecorator) obj;
        return Objects.equals(this.delegate, other.delegate);
    }
}
