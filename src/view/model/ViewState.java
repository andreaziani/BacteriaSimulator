package view.model;

import java.util.Map;

import view.model.food.ViewFood;

/** 
 * ViewState contains information of objects present in the simulation.
 *
 */
public interface ViewState {
    /**
     * Get the state of insertedFoods.
     * @return The unmodifiable map of ViewPosition-ViewFood that specify 
     * in which position they are located ViewFoods inserted.
     */
    Map<ViewPosition, ViewFood> getFoodsState();
}
