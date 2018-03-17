package model.bacteria;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import utils.exceptions.AlreadyExistingSpeciesExeption;

/**
 * Implementation of SpeciesManager, contains all the Species added to a
 * Simulation uniquely identified by name.
 */
public class SpeciesManagerImpl implements SpeciesManager {

    private final Map<String, Species> speciesMap;

    /**
     * Create an empty SpeciesManager.
     */
    public SpeciesManagerImpl() {
        speciesMap = new HashMap<>();
    }

    @Override
    public void addSpecies(final Species species) {
        if (speciesMap.containsKey(species.getName())) {
            throw new AlreadyExistingSpeciesExeption();
        }
        speciesMap.put(species.getName(), species);
    }

    @Override
    public Set<Species> getSpecies() {
        return speciesMap.values().stream().collect(Collectors.toSet());
    }

}
