package model.action;

/**
 * Abstract implementation of Action. It is a complete implementation but cannot
 * be instantiated because different ActionType require different implementation
 * of Action.
 */
public class AbstractAction implements Action {

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

}
