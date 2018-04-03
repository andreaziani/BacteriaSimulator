package model;

import java.util.Map;

import model.bacteria.Bacteria;
import model.food.Food;

/**
 * Environment's State, contains information of bacteria, foods and all the
 * helpful information.
 * 
 */
public interface State {
    /**
     * 
     * @return an unmodifiable copy of the state of Foods present in the simulation.
     */
    Map<Position, Food> getFoodsState();

    /**
     * @return an unmodifiable copy of the state of Bacteria present in the simulation.
     */
    Map<Position, Bacteria> getBacteriaState();
}
