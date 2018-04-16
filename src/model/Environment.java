package model;

import java.util.List;

import model.food.Food;
import model.state.InitialState;
import model.state.Position;
import model.state.State;

/**
 * Environment of the simulation, deals with all the actions in the simulation.
 * 
 *
 */
public interface Environment {

    /**
     * Initialize the environment inserting elements if none are present.
     * 
     * @throws IllegalStateException
     *             if the initial state of the environment in inconsistent with the
     *             species added to the environment.
     */
    void initialize();

    /**
     * Get all the existing foods.
     * 
     * @return an unmodifiable copy of the list of existing foods.
     */
    List<Food> getExistingFoods();

    /**
     * return the EnvState.
     * 
     * @return EnvState state of env.
     */
    State getState();

    /**
     * update environment.
     */
    void update();

    /**
     * return the analysis of the species.
     * 
     * @return Analysis of fitness.
     */
    Analysis getAnalysis();

    /**
     * Get the maximum position in the environment.
     * 
     * @return the maximum position.
     */
    Position getMaxPosition();

    /**
     * @return if the simulation is over.
     */
    boolean isSimulationOver();

    /**
     * @return the initial state of the environment.
     */
    InitialState getInitialState();
}
