package model.bacteria.species;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of SpeciesManager, contains all the Species added to a
 * Simulation uniquely identified by name.
 */
public final class SpeciesManagerImpl implements SpeciesManager {

    private final Map<String, Species> speciesMap;

    /**
     * Create an empty SpeciesManager.
     */
    public SpeciesManagerImpl() {
        speciesMap = new HashMap<>();
    }

    @Override
    public void addSpecies(final SpeciesOptions species) {
        if (speciesMap.containsKey(species.getName())) {
            throw new AlreadyExistingSpeciesExeption();
        }
        final SpeciesBuilder builder = new SpeciesBuilder(species.getName());
        species.getDecisionOptions().forEach(builder::addDecisionMaker);
        species.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
        speciesMap.put(species.getName(), builder.build());
    }

    @Override
    public Set<Species> getSpecies() {
        return speciesMap.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Species getSpeciesByName(final String name) {
        if (speciesMap.containsKey(name)) {
            return speciesMap.get(name);
        } else {
            throw new NoSuchElementException();
        }
    }

}
