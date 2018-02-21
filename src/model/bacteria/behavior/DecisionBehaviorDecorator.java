package model.bacteria.behavior;


/**
 * Abstract implementation of Behavior that follows the decorator pattern. The
 * method chooseAction is delegated to the Behavior taken by the constructor.
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
    protected void updateDecisionSet() {
        this.delegate.updateDecisionSet();
    }
}
