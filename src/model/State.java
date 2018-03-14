package model;

import java.util.Map;

import model.food.Food;

/** 
 * Environment's State, contains information of foods, bacteria
 * and all the helpful information.
 * 
 */
public interface State {
    /**
     * 
     * @return an unmodifiable copy of the state of Foods present in the simulation.
     */
    Map<Position, Food> getFoodsState();
}
