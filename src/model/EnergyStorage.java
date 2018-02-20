package model;

import model.food.Food;

/**
 * Interface that represents the internal storage of energy of a bacteria.
 */
public interface EnergyStorage {
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
}
