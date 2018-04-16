package model.simulator;

import java.util.Map;
import java.util.Optional;

import model.bacteria.Bacteria;
import model.state.Position;

/**
 * Interface used to update Bacteria every turn.
 *
 */
public interface BacteriaManager {
    /**
     * Update all Bacteria.
     */
    void updateBacteria();

    /**
     * Return bacteria state.
     * @return the state of bacteria in the environment
     */
    Map<Position, Bacteria> getBacteriaState();

    void populate(Optional<Map<Position, Bacteria>> bacteriaState);
}
