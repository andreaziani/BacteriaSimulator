package model.bacteria;

import java.util.Set;

/**
 * Represents the component of the environment that stores all the Species
 * created and used in the simulation.
 */
public interface SpeciesManager {
    /**
     * Add a Species to the manager.
     * 
     * @param species
     *            a Species to be added.
     * @throws AlreadyExistingSpeciesExeption
     *             if the name given to the Species is already associated to another
     *             Species.
     */
    void addSpecies(Species species);

    /**
     * @return a Set containing all the Species added to the manager.
     */
    Set<Species> getSpecies();
}
