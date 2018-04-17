package view.model.bacteria;


import java.util.Objects;

import model.bacteria.species.SpeciesOptions;
import view.Radius;

/**
 * Implementation of ViewBacteria that uses a SpeciesOptions.
 */
public final class ViewBacteriaImpl implements ViewBacteria {
    private final Radius radius;
    private final SpeciesOptions species;

    /**
     * Create a new ViewBacteria with a given radius and of a given species.
     * 
     * @param radius
     *            the radius of the bacteria.
     * @param species
     *            the species of the bacteria.
     */
    public ViewBacteriaImpl(final Radius radius, final SpeciesOptions species) {
        this.radius = radius;
        this.species = species;
    }

    @Override
    public Radius getRadius() {
        return this.radius;
    }

    @Override
    public SpeciesOptions getSpecies() {
        return this.species;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, species);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ViewBacteria other = (ViewBacteria) obj;
        return Objects.equals(this.radius, other.getRadius()) && Objects.equals(this.species, other.getSpecies());
    }
}
