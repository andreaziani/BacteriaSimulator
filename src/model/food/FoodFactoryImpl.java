package model.food;

import java.util.Map;
import java.util.Optional;

/**
 * Factory to create foods in different ways.
 * 
 *
 */
public final class FoodFactoryImpl implements FoodFactory {

    @Override
    public Food createFoodFromNutrients(final Map<Nutrient, Double> nutrients) {
        return new FoodImpl(Optional.empty(), nutrients);
    }

    @Override
    public Food createFoodFromNameAndNutrients(final String name, final Map<Nutrient, Double> nutrients) {
        return new FoodImpl(Optional.of(name), nutrients);
    }

}
