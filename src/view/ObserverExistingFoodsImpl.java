package view;

import java.util.Set;

import view.food.ViewFood;
/**
 * Implementation of ObserverExistingFoods.
 *
 */
public class ObserverExistingFoodsImpl implements ObserverExistingFoods {
    private Set<ViewFood> existingFoods;
    @Override
    public void update(final Set<ViewFood> existingFoods) {
        this.existingFoods = existingFoods;
    }
    @Override
    public Set<ViewFood> getTypeOfFoods() {
        return this.existingFoods;
    }

}
