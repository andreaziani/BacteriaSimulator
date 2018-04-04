package model.simulator;

import java.util.Map;

import model.Position;
import model.bacteria.Bacteria;

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
     * @return the state of bacteria in the enviroment
     */
    Map<Position, Bacteria> getBacteriaState();
}
