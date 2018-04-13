package model.food;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of Food. A "Food" is composed by a name and contains the
 * nutrients in their quantity.
 *
 *
 */
public final class FoodImpl implements Food {
    private static final double RADIUS = 7.0;
    private final Optional<String> name;
    private final Map<Nutrient, Double> nutrients;

    /**
     * Constructor of food from name and nutrients.
     * 
     * @param name
     *            the name of the food.
     * @param nutrients
     *            the nutrients of the food.
     */
    public FoodImpl(final String name, final Map<Nutrient, Double> nutrients) {
        this.name = Optional.of(name);
        this.nutrients = nutrients;
    }

    /**
     * Constructor of food from nutrients.
     * 
     * @param nutrients
     *            the nutrients of the food.
     */
    public FoodImpl(final Map<Nutrient, Double> nutrients) {
        this.name = Optional.empty();
        this.nutrients = nutrients;
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
        return this.name.orElse(null); // if the name is present return the name, else return null (this is a bacteria that's dead).
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

    @Override
    public String toString() {
        return "Food's Name=" + name + ", nutrients=" + nutrients;
    }
}
