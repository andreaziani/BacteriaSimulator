package model.bacteria;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.Energy;
import model.EnergyImpl;
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
    private Energy reserve;
    private Function<Nutrient, Energy> nutrientToEnergyConverter;

    /**
     * Creates an empty nutrient storage and initialize a function to convert
     * nutrients to energy.
     * 
     * @param nutrientToEnergyConverter
     *            a function that associates nutrients and energy.
     */
    public NutrientStorage(final Function<Nutrient, Energy> nutrientToEnergyConverter) {
        this(EnergyImpl.ZERO, nutrientToEnergyConverter);
    }

    /**
     * Creates a nutrient storage with a starting Energy reserve and initialize a
     * function to convert nutrients to energy.
     * 
     * @param startingEnergy
     *            the initial energy of the storage.
     * @param nutrientToEnergyConverter
     *            a function that associates nutrients and energy.
     */
    public NutrientStorage(final Energy startingEnergy, final Function<Nutrient, Energy> nutrientToEnergyConverter) {
        this.reserve = startingEnergy;
        this.store = new EnumMap<>(Nutrient.class);
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
        this.reserve = EnergyImpl.ZERO;
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
        food.getNutrients().stream().forEach(
                n -> this.store.merge(n, food.getQuantityFromNutrient(n), (oldValue, newValue) -> oldValue + newValue));
    }

    @Override
    public void takeEnergy(final Energy energy) { // TODO very bad implementation
        if (this.getEnergyStored().getAmount() < energy.getAmount()) {
            throw new NotEnounghEnergyException();
        }
        if (energy.compareTo(this.reserve) <= 0) {
            this.reserve = this.reserve.subtract(energy);
        } else {
            final Energy remained = energy.subtract(this.reserve);
            this.reserve = EnergyImpl.ZERO;

            final List<Nutrient> orderedNutrients = this.store.keySet().stream().sorted(
                    (n1, n2) -> (int) (this.totalEnergyStoredPerNutrient(n2) - this.totalEnergyStoredPerNutrient(n1)))
                    .collect(Collectors.toList());
            for (int i = 0; i < orderedNutrients.size() && remained.compareTo(EnergyImpl.ZERO) > 0; i++) {
                final Nutrient nutrient = orderedNutrients.get(i);
                final double nutrientValue = this.nutrientToEnergyConverter.apply(nutrient).getAmount();
                final double necessary = remained.getAmount() / nutrientValue;
                remained.subtract(new EnergyImpl(nutrientValue * Math.min(necessary, this.store.get(nutrient))));
                this.store.put(nutrient, this.store.get(nutrient) - Math.min(necessary, this.store.get(nutrient)));
            }
        }
    }

    private double totalEnergyStoredPerNutrient(final Nutrient nutrient) {
        return this.nutrientToEnergyConverter.apply(nutrient).getAmount() * this.store.get(nutrient);
    }

    @Override
    public Energy getEnergyStored() {
        return new EnergyImpl(
                this.store.keySet().stream().mapToDouble(this::totalEnergyStoredPerNutrient).filter(x -> x > 0).sum());
    }

    /**
     * @return an unmodifiable map that associates each nutrient in the bacteria to
     *         the amount stored.
     */
    public Map<Nutrient, Double> getNutrients() {
        return Collections.unmodifiableMap(this.store);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nutrientToEnergyConverter == null) ? 0 : nutrientToEnergyConverter.hashCode());
        result = prime * result + ((reserve == null) ? 0 : reserve.hashCode());
        result = prime * result + ((store == null) ? 0 : store.hashCode());
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
        final NutrientStorage other = (NutrientStorage) obj;
        if (nutrientToEnergyConverter == null) {
            if (other.nutrientToEnergyConverter != null) {
                return false;
            }
        } else if (!nutrientToEnergyConverter.equals(other.nutrientToEnergyConverter)) {
            return false;
        }
        if (reserve == null) {
            if (other.reserve != null) {
                return false;
            }
        } else if (!reserve.equals(other.reserve)) {
            return false;
        }
        if (store == null) {
            if (other.store != null) {
                return false;
            }
        } else if (!store.equals(other.store)) {
            return false;
        }
        return true;
    }

}
