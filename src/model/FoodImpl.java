package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/** Implementation of food.
 * 
 *
 *
 */
public class FoodImpl implements Food {
    private Map<Nutrients, Double> nutrients = new HashMap<>();
    FoodImpl(final FoodBuilder builder) {
        this.nutrients = builder.nutrients;
    }
    @Override
    public final Set<Nutrients> getNutrients() {
        return this.nutrients.keySet();
    }

    @Override
    public final double getQuantityFromNutrients(final Nutrients nutrient) {
        if (!this.nutrients.containsKey(nutrient)) {
            return 0.0;
        }
        return this.nutrients.get(nutrient);
    }

/** Builder for food.
 * 
 *
 *
 */
    public class FoodBuilder {
        private final Map<Nutrients, Double> nutrients = new HashMap<>();
        private boolean built;
        /** Add nutrients to builder.
         * 
         * @param nutrient to add.
         * @param quantity of nutrient.
         * @return this builder.
         */
        public FoodBuilder addNutrients(final Nutrients nutrient, final double quantity) {
            this.nutrients.putIfAbsent(nutrient, quantity);
            return this;
        }
        /** Build a food.
         * 
         * @return the food.
         */
        public Food build() {
            if (!this.nutrients.isEmpty() && !this.built) {
                this.built = true;
                return new FoodImpl(this);
            }
            throw new IllegalStateException();
        }
    }
}
