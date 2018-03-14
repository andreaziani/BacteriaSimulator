package view;

import java.awt.Color;

/**
 * Representation of a Species for the View.
 */
public class ViewSpecies {
    private final String name;
    private final Color color;

    /**
     * Create a new ViewSpecies, assigning a name and a color.
     * 
     * @param name
     *            the name of the species.
     * @param color
     *            the color of the bacteria of this species.
     */
    public ViewSpecies(final String name, final Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * @return the name of the species.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the color of the bacteria of this species.
     */
    public Color getColor() {
        return this.color;
    }
}
