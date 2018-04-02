package view.model.bacteria;

import java.awt.Color;
import java.util.Objects;

/**
 * Implementation of ViewBacteria that uses a ViewSpecies.
 */
public class ViewBacteriaImpl implements ViewBacteria {
    private final double radius;
    private final ViewSpecies species;

    /**
     * Create a new ViewBacteria with a given radius and of a given species.
     * 
     * @param radius
     *            the radius of the bacteria.
     * @param species
     *            the species of the bacteria.
     */
    public ViewBacteriaImpl(final double radius, final ViewSpecies species) {
        this.radius = radius;
        this.species = species;
    }

    @Override
    public double getRadius() {
        return this.radius;
    }

    @Override
    public Color getColor() {
        return this.species.getColor();
    }

    @Override
    public ViewSpecies getSpecies() {
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
