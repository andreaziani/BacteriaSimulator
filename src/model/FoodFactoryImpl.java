package model;

import java.util.Map;

import model.FoodImpl.FoodBuilder;
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

}
