package model.action;

import java.util.Objects;

/**
 * Abstract implementation of Action. It is a complete implementation but cannot
 * be instantiated because different ActionType require different implementation
 * of Action.
 */
public abstract class AbstractAction implements Action {

    private final ActionType type;

    /**
     * Create an action given an ActionType.
     * 
     * @param type
     *            an ActionType.
     */
    public AbstractAction(final ActionType type) {
        this.type = type;
    }

    @Override
    public final ActionType getType() {
        return this.type;
    }

    /**
     * Hash the Action based only on its type. A subclass should consider using the
     * superclass hash and ignore the type.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    /**
     * Compare if the runtime class of the objects are equal. Then compare if their
     * types are equal.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AbstractAction other = (AbstractAction) obj;
        return Objects.equals(type, other.type);
    }

}
