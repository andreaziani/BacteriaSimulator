package view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import model.Nutrient;

/**
 * The class representing a food in the view.
 * Two foods are the same type of food if they have the same name.
 *
 */
public class ViewFoodImpl implements ViewFood {
    private final String name;
    private final ViewPosition pos;
    private final Map<Nutrient, Double> nutrients = new HashMap<>();
    private ViewFoodImpl(final ViewFoodBuilder builder) {
        this.name = builder.name;
        builder.nutrients.keySet().stream().forEach(k -> this.nutrients.put(k, builder.nutrients.get(k)));
        this.pos = builder.pos;
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
    public ViewPosition getPosition() {
        return this.pos;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
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
        final ViewFoodImpl other = (ViewFoodImpl) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    //TODO mi sa molto di copia.
    /**
     * Builder for ViewFood.
     * Allows you to create a food by adding name and nutrients, 
     * when food is built it is no longer possible to change its nutrients.
     *
     */
    public static class ViewFoodBuilder {
        private final Map<Nutrient, Double> nutrients = new HashMap<>();
        private String name;
        private ViewPosition pos;
        private boolean built;
        /**
         * Set the name of the food.
         * @param name of the food.
         * @return this builder.
         */
        public ViewFoodBuilder setName(final String name) {
            checkBuilt();
            this.name = name;
            return this;
        }
        /**
         * Insert the position of the food in the view.
         * @param pos of the food.
         * @return this builder.
         */
        public ViewFoodBuilder setPosition(final ViewPosition pos) {
            checkBuilt();
            this.pos = pos;
            return this;
        }
        /**
         * Add nutrients to builder.
         * 
         * @param nutrients to add.
         * @return this builder.
         */
        public ViewFoodBuilder addNutrient(final Entry<Nutrient, Double> nutrients) {
            checkBuilt();
            this.nutrients.put(nutrients.getKey(), nutrients.getValue());
            return this;
        }
        /**
         * Build a food.
         * 
         * @return the food built.
         */
        public ViewFood build() {
            if (!this.nutrients.isEmpty() && this.pos != null && this.name != null && !this.built) {
                this.built = true;
                return new ViewFoodImpl(this);
            }
            throw new IllegalStateException();
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