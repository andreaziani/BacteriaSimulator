package view.model.food;

import java.awt.Color;

import view.Radius;

/**
 * Representation of a provision in the view.
 * A provision is a representation of a food in the gui.
 *
 */
public interface ViewProvision {
    /**
     * @return the radius of the bacteria.
     */
    Radius getRadius();

    /**
     * @return a color representing the Species of the bacteria.
     */
    Color getColor();
}
