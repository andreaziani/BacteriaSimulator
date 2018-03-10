package model.action;

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
    public ActionType getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractAction other = (AbstractAction) obj;
        return type != other.type;
    }

}
