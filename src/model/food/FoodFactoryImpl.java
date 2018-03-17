package model.food;

import java.util.Map;

/** 
 * Factory to create foods.
 * 
 *
 */
public class FoodFactoryImpl implements FoodFactory {

    @Override
    public Food createFoodFromNutrients(final Map<Nutrient, Double> nutrients) {
        return new FoodImpl(nutrients);
    }

    @Override
    public Food createFoodFromNameAndNutrients(final String name, final Map<Nutrient, Double> nutrients) {
        return new FoodImpl(name, nutrients);
    }

}
