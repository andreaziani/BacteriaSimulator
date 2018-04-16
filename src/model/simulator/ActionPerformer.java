package model.simulator;

import java.util.Optional;

import model.Direction;
import model.bacteria.Bacteria;
import model.state.Position;

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
     * @param moveDistance the maximum distance the bacteria should move of
     */
    void move(Direction moveDirection, double moveDistance);

    /**
     * Perform the action EAT on the food in given position.
     * @param foodPosition the position of the food to eat.
     */
    void eat(Optional<Position> foodPosition);

    /**
     * 
     * @param bacteriaCounter
     *            the next id of the bacteria.
     * @return if the bacteria has been added.
     */
    boolean replicate(int bacteriaCounter);

    /**
     * Perform the action NOTHING.
     */
    void doNothing();
}