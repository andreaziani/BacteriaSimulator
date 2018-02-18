package model;

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
     */
    void takeEnergy(Energy energy);

    /**
     * @return the total amount of energy stored.
     */
    Energy getEnergyStored();
}
