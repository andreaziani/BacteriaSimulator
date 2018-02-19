package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import utils.NotEnounghEnergyException;

public class NutrientStorage implements EnergyStorage {

    private final Map<Nutrient, Double> store;
    private Function<Nutrient, Energy> nutrientToEnergyConverter;

    public NutrientStorage(Function<Nutrient, Energy> nutrientToEnergyConverter) {
        this.store = new HashMap<>();
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
    }

    public void setNutrientToEnergyConverter(Function<Nutrient, Energy> nutrientToEnergyConverter) {
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
    }

    @Override
    public void storeFood(final Food food) {
        food.getNutrients().stream().forEach(n -> this.store.merge(n, food.getQuantityFromNutrients(n),
                (oldValue, newValue) -> oldValue + newValue));
    }

    @Override
    public void takeEnergy(final Energy energy) {
        if (this.getEnergyStored().getAmount() < energy.getAmount()) {
            throw new NotEnounghEnergyException();
        }
    }

    @Override
    public Energy getEnergyStored() {
        return () -> this.store.keySet().stream()
                .mapToDouble(n -> this.nutrientToEnergyConverter.apply(n).getAmount() * this.store.get(n))
                .filter(x -> x > 0).sum();
    }

    public Map<Nutrient, Double> getNutrients() {
        return Collections.unmodifiableMap(this.store);
    }

}
