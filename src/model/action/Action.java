package model.action;

/**
 * An action that a Bacteria can choose.
 */
public interface Action {
    /**
     * @return the type of the action
     */
    ActionType getType();
}
