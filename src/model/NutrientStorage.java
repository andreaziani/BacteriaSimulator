package model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NutrientStorage implements EnergyStorage {

    private final Map<Nutrient, Double> store;

    public NutrientStorage() {
        store = new HashMap<>();
    }

    @Override
    public void storeFood(Food food) {
        food.getNutrients().stream().forEach(
                n -> store.merge(n, food.getQuantityFromNutrients(n), (oldValue, newValue) -> oldValue + newValue));
    }

    @Override
    public void takeEnergy(Energy energy) {
        // TODO Auto-generated method stub

    }

    @Override
    public Energy getEnergyStored() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<Nutrient, Double> getNutrients() {
        // TODO Auto-generated method stub
        return null;
    }

}
