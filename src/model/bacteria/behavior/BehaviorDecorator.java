package model.bacteria.behavior;

import model.action.Action;
import model.perception.Perception;

/**
 * Abstract implementation of Behavior that follows the decorator pattern. The
 * method chooseAction is delegated to the Behavior taken by the constructor.
 */
public abstract class BehaviorDecorator implements Behavior {

    private final Behavior delegate;

    /**
     * Construct a new BehaviorDecorator by taking a delegate.
     * 
     * @param delegate
     *            a Behavior.
     */
    public BehaviorDecorator(final Behavior delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public Action chooseAction(final Perception perception) {
        return delegate.chooseAction(perception);
    }

}
