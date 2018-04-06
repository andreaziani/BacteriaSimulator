package model.simulator;

import model.Direction;
import model.Position;
import model.bacteria.Bacteria;

/**
 * Interface whose task is to make each Bacteria perform an Action.
 *
 */
public interface ActionPerformer {
    /**
     * Set the status for the ActionPerformer, namely the parameter it should work with.
     * @param bacteriumPos the position of the bacterium
     * @param bacterium the actor of which perform the action
     */
    void setStatus(Position bacteriumPos, Bacteria bacterium);

    /**
     * Perform the action MOVE.
     * @param moveDirection the direction in which the bacteria should move
     */
    void move(Direction moveDirection);

    /**
     * Perform the action EAT.
     */
    void eat();

    /**
     * Perform the action REPLICATE.
     */
    void replicate();

    /**
     * Perform the action NOTHING.
     */
    void doNothing();
}