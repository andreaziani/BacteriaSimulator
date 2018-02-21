package model.action;

/**
 * An implementation of action that contains only the action type. Can only be
 * used with Eat, Nothing and Replicate action types.
 */
public class SimpleAction extends AbstractAction {

    /**
     * Create a simple action given an ActionType.
     * 
     * @param type
     *            an ActionType.
     * @throws IllegalArgumentExeption
     *             if type is not Eat, Nothing or Replicate
     */
    public SimpleAction(final ActionType type) {
        super(type);
        if (type != ActionType.NOTHING && type != ActionType.EAT && type != ActionType.REPLICATE) {
            throw new IllegalArgumentException("Wrong action type");
        }

    }
}
