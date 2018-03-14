package view;

import java.awt.Color;

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
}
