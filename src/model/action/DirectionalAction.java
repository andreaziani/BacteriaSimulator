package model.action;

import model.Direction;

/**
 * An action that is directed. Represent a Move action type.
 */
public interface DirectionalAction extends Action {
    /**
     * @return the direction in which this action must be executed.
     */
    Direction getDirection();
}
