package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/** Implementation of food.
 * 
 *
 *
 */
public final class FoodImpl implements Food {
    private final String name;
    private final Set<Nutrients> nutrients = new HashSet<>(); // TO DO: decidere bene come gestirlo.
    private FoodImpl(final FoodBuilder builder) {
        this.name = builder.name;
        this.nutrients.addAll(builder.nutrients);
    }
    @Override
    public Set<Nutrients> getNutrients() {
        return this.nutrients;
    }

    @Override
    public double getQuantityFromNutrients(final Nutrients nutrient) {
        return 0.0; // TO FIX.
    }

    @Override
    public String getFoodName() {
        // TODO Auto-generated method stub
        return this.name;
    }
/** Builder for food.
 * 
 *
 *
 */
    public final class FoodBuilder {
        private String name;
        private final Set<Nutrients> nutrients = new HashSet<>();
        private FoodBuilder() { }
        /** Setter for the name.
         * 
         * @param name to set.
         * @return this builder.
         */
        public FoodBuilder setName(final String name) {
            this.name = name;
            return this;
        }
        /** Setter for the nutrients.
         * 
         * @param nutrients to set.
         * @return this builder.
         */
        public FoodBuilder setNutrients(final Nutrients... nutrients) {
            this.nutrients.addAll(Arrays.asList(nutrients));
            return this;
        }
        /** Build a food.
         * 
         * @return the food.
         */
        public Food build() {
            return new FoodImpl(this);
        }
    }
}
