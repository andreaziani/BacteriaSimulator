package view.model.bacteria;

import java.awt.Color;

import view.Radius;

/**
 * Representation of a Bacteria for the View.
 *
 */
public interface ViewBacteria {
    /**
     * @return the radius of the bacteria.
     */
    Radius getRadius();

    /**
     * @return a color representing the Species of the bacteria.
     */
    Color getColor();

    /**
     * @return the Species of the Bacteria.
     */
    ViewSpecies getSpecies();
}
