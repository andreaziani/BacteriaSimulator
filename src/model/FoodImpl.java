package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Implementation of Food. 
 * A "Food" contains the nutrients that compose the food
 * and their quantity.
 *
 *
 */
public class FoodImpl implements Food {
    private final Map<Nutrient, Double> nutrients = new HashMap<>();
    /** Constructor packege private.
     * 
     * @param builder that create food.
     */
    private FoodImpl(final FoodBuilder builder) {
        builder.nutrients.keySet().stream().forEach(n -> this.nutrients.put(n, builder.nutrients.get(n)));
    }

    @Override
    public Set<Nutrient> getNutrients() {
        return this.nutrients.keySet();
    }

    @Override
    public double getQuantityFromNutrient(final Nutrient nutrient) {
        if (!this.nutrients.containsKey(nutrient)) {
            return 0.0;
        }
        return this.nutrients.get(nutrient);
    }

    //TODO hashcode.
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nutrients == null) ? 0 : nutrients.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FoodImpl other = (FoodImpl) obj;
        if (nutrients == null) {
            if (other.nutrients != null) {
                return false;
            }
        } else if (!nutrients.keySet().containsAll(other.nutrients.keySet()) || !other.nutrients.keySet().containsAll(this.nutrients.keySet())) {
            return false;
        } else if (!this.nutrients.keySet().stream().allMatch(k -> other.nutrients.get(k).doubleValue() == this.nutrients.get(k).doubleValue())) {
              return false;
        }
        return true;
    }

    /**
     * Builder for food.
     * Allows you to create a food by adding nutrients, 
     * when food is built it is no longer possible to change its nutrients.
     *
     *
     */
    public static class FoodBuilder {
        private final Map<Nutrient, Double> nutrients = new HashMap<>();
        private boolean built;
        /**
         * Add nutrients to builder.
         * 
         * @param nutrients to add.
         * @return this builder.
         */
        public FoodBuilder addNutrient(final Entry<Nutrient, Double> nutrients) {
            if (built) {
                throw new IllegalStateException();
            } else {
                this.nutrients.put(nutrients.getKey(), nutrients.getValue());
                return this;
            }
        }
        /**
         * Build a food.
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
