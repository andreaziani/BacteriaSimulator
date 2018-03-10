package model.food;

import java.util.Map;
import model.food.FoodImpl.FoodBuilder;

/** 
 * Factory to create foods.
 * 
 *
 */
public class FoodFactoryImpl implements FoodFactory {

    @Override
    public Food createFoodFromNutrients(final Map<Nutrient, Double> nutrients) {
        final FoodBuilder builder = new FoodBuilder();
        nutrients.entrySet().forEach(e -> builder.addNutrient(e));
        return builder.build();
    }

    @Override
    public Food createFoodFromNameAndNutrients(final String name, final Map<Nutrient, Double> nutrients) {
        final FoodBuilder builder = new FoodBuilder();
        nutrients.entrySet().forEach(e -> builder.addNutrient(e));
        return builder.setName(name).build();
    }

}
