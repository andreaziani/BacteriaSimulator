package view.gui;

import java.awt.Color;

import view.model.bacteria.ViewSpecies;
import view.model.food.ViewProvision;

/**
 * Represent an object that has utilities for other components of the gui to
 * assign colors to species and foods.
 */
public interface ColorAssigner {
    /**
     * Assign a color to a food.
     * 
     * @param food
     *            a food.
     * @return a color assigned to that food.
     */
    Color getColorFromFood(ViewProvision food);

    /**
     * Assign a color to a species.
     * 
     * @param species
     *            a species.
     * @return a color assigned to that species.
     */
    Color getColorFromSpecies(ViewSpecies species);
}
