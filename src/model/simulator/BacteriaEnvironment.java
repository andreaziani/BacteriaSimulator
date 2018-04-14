package model.simulator;

import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

import model.Position;
import model.bacteria.Bacteria;

/**
 * Interface that represent all Bacteria in the simulation, provide method for its update.
 */
public interface BacteriaEnvironment {
    /**
     * Check whether the BacteriaEnvironment contains a Bacteria in given Position.
     * @param pos the position to be checked
     * @return true if the Position if occupied by a Bacteria, false otherwise
     */
    boolean containBacteriaInPosition(final Position pos);
    /**
     * Change the position of a Bacteria from oldPos to newPos.
     * @param oldPos the oldPosition in which the Bacteria is located
     * @param newPos the newPosition where the Bacteria should be moved
     */
    void changeBacteriaPosition(final Position oldPos, final Position newPos);
    /**
     * Get the bacteria located in given Position.
     * @param pos the position of the Bacteria to be retrieved
     * @return the Bacteria
     */
    Bacteria getBacteria(final Position pos);
    /**
     * Add new Bacteria to the BacteriaEnviroment.
     * @param position the Position in which insert the Bacteria
     * @param bacteria the Bacteria to insert
     */
    void insertBacteria(final Position position, final Bacteria bacteria);
    /**
     * Return the Set of Entry<Position, Bacteria> representing the BacteriaEnvironment.
     * @return the Set
     */
    Set<Entry<Position, Bacteria>> entrySet();
    /**
     * Return the Set of occupied Position in the environment.
     * @return such Set
     */
    Set<Position> activePosition();
    /**
     * Remove a set of Position from the BacteriaEnvironment.
     * @param positions the set of Position to be removed
     */
    void removeFromPositions(final Set<Position> positions);
    /**
     * Return the state of the BacteriaEnviroment.
     * @return an unmodifiable map represent the state
     */
    Map<Position, Bacteria> getBacteriaState();

    /**
     * Update all Position that a Bacteria occupies within its Radius.
     */
    void updateOccupiedPositions();

    /**
     * Check whether a Position is occupied by another Bacteria.
     * @param position the position that have to be checked
     * @return true if the position is occupied, false otherwise
     */
    boolean isPositionOccupied(Position position);

    /**
     * Set all Position occupied by the Bacteria in given position as not occupied.
     * @param position the Position of the Bacteria
     */
    void clearPosition(Position position);

    /**
     * Set all Position occupied by the Bacteria in given position as occupied.
     * @param position the Position of the Bacteria
     */
    void setPosition(Position position);
}
