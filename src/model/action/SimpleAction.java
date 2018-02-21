package model.action;
/**
 * An implementation of action that contains only the action type. Can only be used by Eat, Nothing and Replicate action types.
 */
public class SimpleAction implements Action {

    private final ActionType type;
    /**
     * Create a simple action given an ActionType.
     * @param type an ActionType.
     * @throws IllegalArgumentExeption if type is not Eat, Nothing or Replicate
     */
    public SimpleAction(final ActionType type) {
        if (type != ActionType.NOTHING && type != ActionType.EAT && type != ActionType.REPLICATE) {
            throw new IllegalArgumentException("Wrong action type");
        }
        this.type = type;
    }

    @Override
    public ActionType getType() {
        return this.type;
    }

}
