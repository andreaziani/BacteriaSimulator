package model.food;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of Food. A "Food" contains the nutrients that compose the food
 * and their quantity.
 *
 *
 */
public class FoodImpl implements Food {
    private static final double RADIUS = 1.0;
    private final Optional<String> name;
    private final Map<Nutrient, Double> nutrients = new HashMap<>();

    /**
     * 
     * Constructor of food from FoodBuilder.
     * Package private.
     * @param builder
     *            FoodBuilder that creates food.
     */
    FoodImpl(final FoodBuilder builder) {
        builder.nutrients.keySet().stream().forEach(n -> this.nutrients.put(n, builder.nutrients.get(n)));
        this.name = builder.name;
    }

    @Override
    public Set<Nutrient> getNutrients() {
        return Collections.unmodifiableSet(this.nutrients.keySet());
    }

    @Override
    public double getQuantityFromNutrient(final Nutrient nutrient) {
        if (!this.nutrients.containsKey(nutrient)) {
            return 0.0;
        }
        return this.nutrients.get(nutrient);
    }

    @Override
    public String getName() {
        return this.name.get(); // if the name is present return the name, else this is a bacteria that's dead.
    }

    @Override
    public double getRadius() {
        return RADIUS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nutrients);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            final FoodImpl other = (FoodImpl) obj;
            return Objects.equals(this.name, other.name)
                    && Objects.equals(this.nutrients.keySet(), other.nutrients.keySet())
                    && this.nutrients.keySet().stream()
                            .allMatch(k -> other.nutrients.get(k).doubleValue() == this.nutrients.get(k).doubleValue());
        }
        return false;
    }

    /**
     * Builder for food. Allows you to create a food by adding nutrients, when food
     * is built it is no longer possible to change its nutrients.
     *
     *
     */
    public static class FoodBuilder {
        private Optional<String> name = Optional.empty();
        private final Map<Nutrient, Double> nutrients = new HashMap<>();
        private boolean built;

        /**
         * Setter the name of the food.
         * 
         * @param name
         *            of the food.
         * @return this builder.
         */
        public FoodBuilder setName(final String name) {
            if (built) {
                throw new IllegalStateException();
            } else {
                this.name = Optional.of(name);
                return this;
            }
        }

        /**
         * Add nutrients to builder.
         * 
         * @param nutrients
         *            to add.
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
