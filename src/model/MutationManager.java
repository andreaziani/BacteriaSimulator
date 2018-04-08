package model;

import java.util.Collection;
import java.util.Map;
import model.bacteria.Bacteria;
/**
 * 
 * Interface of MutationManager.
 *
 */
public interface MutationManager {
    /**
     * Bacteria deleted from environment.
     * @param bacteria
     *          bacteria not found into environment.
     */
    void bacteriaDead(Bacteria bacteria);

    /**
     * Update Mutation of all bacteria.
     * @param bactManager
     *          collection of bacteria.
     */
    void updateMutation(Collection<Bacteria> bactManager);

    /**
     * Get a map of bacteria mutated.
     * @return a map of bacteria mutated.
     */
    Map<Bacteria, Mutation> getMutation();
}
