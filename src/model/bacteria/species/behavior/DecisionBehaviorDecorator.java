package model.bacteria.species.behavior;

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

    /**
     * Abstract method used in the template method updateDecisions that must be
     * implemented by the subclassed of DecisionBehaviorDecorator as if it was
     * updateDecisions of AbstractDecisionBehavior.
     * 
     * @param decisions
     *            the collection of decisions taken until now by the delegated of
     *            the decorator.
     * @param knowledge
     *            the current knowledge of the bacteria making this decisions.
     */
    protected abstract void addDecisions(Map<Action, Double> decisions, BacteriaKnowledge knowledge);

    @Override
    protected final void updateDecisions(final Map<Action, Double> decisions, final BacteriaKnowledge knowledge) {
        this.delegate.updateDecisions(decisions, knowledge);
        this.addDecisions(decisions, knowledge);
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
