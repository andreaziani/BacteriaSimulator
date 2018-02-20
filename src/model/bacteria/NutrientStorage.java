package model.bacteria;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.Energy;
import model.food.Food;
import model.food.Nutrient;
import utils.NotEnounghEnergyException;

/**
 * This class implement an EnergyStorage that does keeps all the nutrients of
 * the food it acquire until it needs to produce energy, then it consume some of
 * the nutrients.
 */
public class NutrientStorage implements EnergyStorage {

    private final Map<Nutrient, Double> store;
    private double reserve;
    private Function<Nutrient, Energy> nutrientToEnergyConverter;

    /**
     * Creates an empty nutrient storage and initialize a function to convert
     * nutrients to energy.
     * 
     * @param nutrientToEnergyConverter
     *            a function that associates nutrients and energy.
     */
    public NutrientStorage(final Function<Nutrient, Energy> nutrientToEnergyConverter) {
        this.store = new EnumMap<>(Nutrient.class);
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
        this.reserve = 0;
    }

    /**
     * @param nutrientToEnergyConverter
     *            a function that associates nutrients and energy, it will replace
     *            the previous one.
     */
    public void setNutrientToEnergyConverter(final Function<Nutrient, Energy> nutrientToEnergyConverter) {
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
    }

    @Override
    public void storeFood(final Food food) {
        food.getNutrients().stream().forEach(n -> this.store.merge(n, food.getQuantityFromNutrient(n),
                (oldValue, newValue) -> oldValue + newValue));
    }

    @Override
    public void takeEnergy(final Energy energy) {
        if (this.getEnergyStored().getAmount() < energy.getAmount()) {
            throw new NotEnounghEnergyException();
        }
        if (energy.getAmount() <= this.reserve) {
            this.reserve -= energy.getAmount();
        } else {
            double remained = energy.getAmount() - this.reserve;
            this.reserve = 0;

            final List<Nutrient> orderedNutrients = this.store.keySet().stream().sorted(
                    (n1, n2) -> (int) (this.totalEnergyStoredPerNutrient(n2) - this.totalEnergyStoredPerNutrient(n1)))
                    .collect(Collectors.toList());
            for (int i = 0; i < orderedNutrients.size() && remained > 0; i++) {
                final Nutrient nutrient = orderedNutrients.get(i);
                final double nutrientValue = this.nutrientToEnergyConverter.apply(nutrient).getAmount();
                final double necessary = remained / nutrientValue;
                remained -= nutrientValue * Math.min(necessary, this.store.get(nutrient));
                this.store.put(nutrient, this.store.get(nutrient) - Math.min(necessary, this.store.get(nutrient)));
            }
        }
    }

    private double totalEnergyStoredPerNutrient(final Nutrient nutrient) {
        return this.nutrientToEnergyConverter.apply(nutrient).getAmount() * this.store.get(nutrient);
    }

    @Override
    public Energy getEnergyStored() {
        return () -> this.store.keySet().stream().mapToDouble(this::totalEnergyStoredPerNutrient).filter(x -> x > 0)
                .sum();
    }

    /**
     * @return an unmodifiable map that associates each nutrient in the bacteria to
     *         the amount stored.
     */
    public Map<Nutrient, Double> getNutrients() {
        return Collections.unmodifiableMap(this.store);
    }

}
