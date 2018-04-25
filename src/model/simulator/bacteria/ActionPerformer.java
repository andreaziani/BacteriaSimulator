package model.simulator.bacteria;

import java.util.Optional;

import model.Direction;
import model.Energy;
import model.bacteria.Bacteria;
import model.state.Position;

/**
 * Interface whose task is to make each Bacteria perform an Action.
 *
 */
public interface ActionPerformer {
    /**
     * Perform the action MOVE.
     * @param position the Position of the Bacteria of which perform the action
     * @param bacterium the Bacteria of which perform the action
     * @param direction the direction in which the bacteria should move
     * @param distance the maximum distance the bacteria should move of
     * @param isSafe representing whether this action is executed on a Bacteria in a safe position
     * @param cost the cost of the action
     */
    void move(Position position, Bacteria bacterium, Direction direction, double distance, boolean isSafe, Energy cost);

    /**
     * Perform the action EAT on the food in given position.
     * @param position the Position of the Bacteria of which perform the action
     * @param bacterium the Bacteria of which perform the action
     * @param foodPosition the position of the food to eat.
     * @param isSafe representing whether this action is executed on a Bacteria in a safe position
     * @param cost the cost of the action
     */
    void eat(Position position, Bacteria bacterium, Optional<Position> foodPosition, boolean isSafe, Energy cost);

    /**
     * @param position the Position of the Bacteria of which perform the action
     * @param bacterium the Bacteria of which perform the action
     * @param isSafe representing whether this action is executed on a Bacteria in a safe position
     * @param cost the cost of the action
     */
    void replicate(Position position, Bacteria bacterium, boolean isSafe, Energy cost);

    /**
     * @param position the Position of the Bacteria of which perform the action
     * @param bacterium the Bacteria of which perform the action
     * @param isSafe representing whether this action is executed on a Bacteria in a safe position
     * @param cost the cost of the action
     */
    void doNothing(Position position, Bacteria bacterium, boolean isSafe, Energy cost);
}
