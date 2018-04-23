package view.model.food;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import model.food.Nutrient;

/**
 * The class representing a food at the user's creation.
 *
 */
public final class CreationViewFoodImpl implements ViewFood {
    private final Optional<String> name;
    private Map<Nutrient, Double> nutrients = new EnumMap<>(Nutrient.class);

    /**
     * Constructor for food created by users.
     * 
     * @param builder
     *            from which take informations.
     */
    private CreationViewFoodImpl(final ConcreteViewFoodBuilder builder) {
        this.name = Optional.of(builder.name);
        builder.nutrients.keySet().stream().forEach(k -> this.nutrients.put(k, builder.nutrients.get(k)));
    }

    /**
     * Constructor for food with possible empty name.
     * 
     * @param name
     *            an optional containing the name of the food.
     * @param nutrients
     *            the nutrients in the food in their quantity.
     */
    public CreationViewFoodImpl(final Optional<String> name, final Map<Nutrient, Double> nutrients) {
        this.name = name;
        this.nutrients = nutrients;
    }

    @Override
    public String getName() {
        return this.name.orElse(null);
    }

    @Override
    public Set<Nutrient> getNutrients() {
        return Collections.unmodifiableSet(this.nutrients.keySet());
    }

    @Override
    public double getQuantityFromNutrient(final Nutrient nutrient) {
        return this.nutrients.get(nutrient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.nutrients);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            final CreationViewFoodImpl other = (CreationViewFoodImpl) obj;
            return Objects.equals(this.name, other.name)
                    && Objects.equals(this.nutrients.keySet(), other.nutrients.keySet())
                    && this.nutrients.keySet().stream()
                            .allMatch(k -> other.nutrients.get(k).doubleValue() == this.nutrients.get(k).doubleValue());
        }
        return false;
    }

    @Override
    public String toString() {
        return "{FOOD name:" + name + ", nutrients:" + nutrients + "}";
    }

    /**
     * Builder for ViewFood. Allows you to create a food by adding name and
     * nutrients, when food is built it is no longer possible to change its
     * nutrients.
     *
     */
    public static class ConcreteViewFoodBuilder implements ViewFoodBuilder {
        private final String name;
        private final Map<Nutrient, Double> nutrients = new HashMap<>();
        private boolean built;

        /**
         * Constructor a FoodBuilder by passing the name of the food.
         * 
         * @param name
         *            the name of the food.
         * @throws NullPointerException
         *             if name is null.
         * @throws IllegalArgumentException
         *             if name isn't correct.
         * 
         */
        public ConcreteViewFoodBuilder(final String name) {
            Objects.requireNonNull(name);
            if (name.isEmpty()) {
                throw new IllegalArgumentException();
            }
            this.name = name;
            this.built = false;
        }

        /**
         * Add nutrients to builder.
         * 
         * @param nutrients
         *            to add.
         * @return this builder.
         */
        public ViewFoodBuilder addNutrient(final Pair<Nutrient, Double> nutrients) {
            checkBuilt();
            this.nutrients.put(nutrients.getLeft(), nutrients.getRight());
            return this;
        }

        /**
         * Build a food.
         * 
         * @return the food built.
         */
        public ViewFood build() {
            this.built = true;
            return new CreationViewFoodImpl(this);
        }

        /**
         * Checking if the builder is built.
         */
        private void checkBuilt() {
            if (built) {
                throw new IllegalStateException();
            }
        }
    }
}
