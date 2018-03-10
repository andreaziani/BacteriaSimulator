package view;

import java.util.Set;

import view.food.ViewFood;

/**
 * 
 * Observer that watch the set of exsisting foods.
 * 
 */
public interface ObserverExistingFoods {
    /**
     * @param existingFoods set of all the type of foods created.
     */ 
    void update(Set<ViewFood> existingFoods);
    /**
     * 
     * @return the last set of existing food's type.
     */
    Set<ViewFood> getTypeOfFoods();
}
