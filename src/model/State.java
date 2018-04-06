package model;

import java.util.Map;

import model.bacteria.Bacteria;
import model.food.Food;

/**
 * Representation of the state of the objects present in the simulation.
 * 
 */
public interface State {
    /**
     * Get the state of foods present in the simulation.
     * 
     * @return an unmodifiable copy of the state of Foods present in the simulation.
     */
    Map<Position, Food> getFoodsState();

    /**
     * Get the state of bacteria present in the simulation.
     * 
     * @return an unmodifiable copy of the state of Bacteria present in the
     *         simulation.
     */
    Map<Position, Bacteria> getBacteriaState();
}
