package model.bacteria;

import java.util.Map;

import model.Energy;
import model.food.Food;
import model.food.Nutrient;

/**
 * Interface that represents the internal storage of nutrients of a bacteria.
 */
public interface NutrientStorage {
    /**
     * @param food
     *            a food to be added to the storage.
     */
    void storeFood(Food food);

    /**
     * @param energy
     *            an amount of energy to be taken away from the storage.
     * @throws NotEnounghEnergyException
     *             if the storage has less energy than required.
     */
    void takeEnergy(Energy energy);

    /**
     * @return the total amount of energy stored.
     */
    Energy getEnergyStored();

    /**
     * @return an unmodifiable map that associates each nutrient in the bacteria to
     *         the amount stored.
     */
    Map<Nutrient, Double> getNutrients();
}
