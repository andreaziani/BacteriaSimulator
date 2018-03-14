package view;

import java.awt.Color;

/**
 * Representation of a Bacteria for the View.
 *
 */
public interface ViewBacteria {
    /**
     * @return the radius of the bacteria.
     */
    double getRadius();

    /**
     * @return a color representing the Species of the bacteria.
     */
    Color getColor();
}
