package view.food;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import model.food.Nutrient;
import view.ViewPosition;

//TODO verificare se si può refattorizzare.

/**
 * The class representing a food in the view.
 *
 */
public class ViewFoodImpl implements ViewFood {
    private final String name;
    private final Map<Nutrient, Double> nutrients = new HashMap<>();
    private ViewFoodImpl(final ViewFoodBuilder builder) {
        this.name = builder.name;
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

    //TODO rifare hashcode.
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        final ViewFoodImpl other = (ViewFoodImpl) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (nutrients == null) {
            if (other.nutrients != null) {
                return false;
            }
        }  else if (!nutrients.keySet().containsAll(other.nutrients.keySet()) || !other.nutrients.keySet().containsAll(this.nutrients.keySet())) {
            return false;
        } else if (!this.nutrients.keySet().stream().allMatch(k -> other.nutrients.get(k).doubleValue() == this.nutrients.get(k).doubleValue())) {
              return false;
        }
        return true;
    }


    /**
     * Builder for ViewFood.
     * Allows you to create a food by adding name and nutrients, 
     * when food is built it is no longer possible to change its nutrients.
     *
     */
    public static class ViewFoodBuilder {
        private final Map<Nutrient, Double> nutrients = new HashMap<>();
        private String name;
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
            if (!this.nutrients.isEmpty() && this.name != null && !this.built) {
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
