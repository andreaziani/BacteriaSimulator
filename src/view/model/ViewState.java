package view.model;

import java.util.Map;

import view.model.bacteria.ViewBacteria;
import view.model.food.ViewProvision;

/**
 * ViewState contains information of objects present in the simulation.
 *
 */
public interface ViewState {
    /**
     * Get the state of insertedFoods.
     * 
     * @return The unmodifiable map of ViewPosition-ViewFood that specify in which
     *         position they are located ViewFoods inserted.
     */
    Map<ViewPosition, ViewProvision> getFoodsState();
    /**
     * Get the state of bacteria.
     * 
     * @return The unmodifiable map of ViewPosition-ViewBacteria that specify in which
     *         position they are located ViewFoods inserted.
     */
    Map<ViewPosition, ViewBacteria> getBacteriaState();
}
