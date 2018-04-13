package view.model.food;


import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.food.Nutrient;

/**
 * 
 * The class representing a food in the ViewState.
 *
 */
public final class SimulationViewFood implements ViewFood {
    private final Optional<String> name;
    private final Map<Nutrient, Double> nutrients;

    /**
     * 
     * @param name
     *            an optional containing the name of the food.
     * @param nutrients
     *            the nutrients in the food in their quantity.
     */
    public SimulationViewFood(final Optional<String> name, final Map<Nutrient, Double> nutrients) {
        this.name = name;
        this.nutrients = nutrients;
    }

    @Override
    public String getName() {
        return name.get();
    }
    /**
     * 
     * @return true if the name is present, false in other case.
     */
    public boolean isNamePresent() {
        return this.name.isPresent();
    }
    @Override
    public Set<Nutrient> getNutrients() {
        return this.nutrients.keySet();
    }

    @Override
    public double getQuantityFromNutrient(final Nutrient nutrient) {
        return this.nutrients.get(nutrient);
    }
}
