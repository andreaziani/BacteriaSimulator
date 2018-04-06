package view.model.food;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import model.food.Nutrient;

/**
 * The class representing a food in the view.
 *
 */
public final class ViewFoodImpl implements ViewFood {
    private final String name;
    private final Color color;
    private final Map<Nutrient, Double> nutrients = new HashMap<>();

    /**
     * Constructor package private. A ViewFood can be built only using
     * ViewFoodBuilder.
     * 
     * @param builder
     *            from which take informations.
     */
    private ViewFoodImpl(final ViewFoodBuilder builder) {
        this.name = builder.name;
        this.color = builder.color;
        builder.nutrients.keySet().stream().forEach(k -> this.nutrients.put(k, builder.nutrients.get(k)));
    }

    @Override
    public String getName() {
        return this.name;
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
            final ViewFoodImpl other = (ViewFoodImpl) obj;
            return Objects.equals(this.name, other.name)
                    && Objects.equals(this.nutrients.keySet(), other.nutrients.keySet())
                    && this.nutrients.keySet().stream()
                            .allMatch(k -> other.nutrients.get(k).doubleValue() == this.nutrients.get(k).doubleValue());
        }
        return false;
    }

    @Override
    public Color getColor() {
        return this.color;
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
    public static class ViewFoodBuilder {
        // required
        private final String name;
        private Color color;
        // optional
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
        public ViewFoodBuilder(final String name) {
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
         * Set the color of the food.
         * 
         * @param color
         *            the color chosen for food.
         * @return this Builder.
         */
        public ViewFoodBuilder addColor(final Color color) {
            checkBuilt();
            this.color = color;
            return this;
        }

        /**
         * Build a food.
         * 
         * @return the food built.
         */
        public ViewFood build() {
            this.built = true;
            return new ViewFoodImpl(this);
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
