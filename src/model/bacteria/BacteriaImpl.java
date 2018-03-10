package model.bacteria;

import model.Energy;
import model.GeneticCode;
import model.action.Action;
import model.bacteria.behavior.Behavior;
import model.food.Food;
import model.food.FoodFactory;
import model.perception.Perception;

/**
 * Implementation of interface Bacteria.
 */
public class BacteriaImpl implements Bacteria {

    private Perception currPerception;
    private GeneticCode geneticCode;
    private final Species species;
    private final Behavior behavior;
    private final EnergyStorage energyStorage;

    /**
     * Construct a Bacteria from a Behavior strategy and a genetic code. This
     * constructor use a nutrientStorage as a default EnergyStorage strategy.
     * 
     * @param species
     *            this bacteria's species.
     * @param initialGeneticCode
     *            a geneticCode to be inserted in the bacteria.
     * @param startingEnergy
     *            the initial Energy of this bacteria.
     */
    public BacteriaImpl(final Species species, final GeneticCode initialGeneticCode, final Energy startingEnergy) {
        this.species = species;
        this.behavior = species.getBehavior();
        this.geneticCode = initialGeneticCode;
        this.energyStorage = new NutrientStorage(startingEnergy, this.geneticCode::getEnergyFromNutrient);
    }

    @Override
    public Perception getPerception() {
        return this.currPerception;
    }

    @Override
    public void setPerception(final Perception perception) {
        this.currPerception = perception;
    }

    @Override
    public Species getSpecies() {
        return this.species;
    }

    @Override
    public Double getRadius() {
        return this.geneticCode.getRadius();
    }

    @Override
    public Double getPerceptionRadius() {
        return this.geneticCode.getPerceptionRadius();
    }

    @Override
    public Action getAction() {
        return this.behavior.chooseAction(this.currPerception, this.geneticCode::getEnergyFromNutrient,
                this::getActionCost, this.getEnergy());
    }

    @Override
    public Energy getActionCost(final Action action) {
        return this.geneticCode.getActionCost(action);
    }

    @Override
    public GeneticCode getGeneticCode() {
        return this.geneticCode;
    }

    @Override
    public void setGeneticCode(final GeneticCode code) {
        this.geneticCode = code;
        if (this.energyStorage.getClass() != NutrientStorage.class) {
            throw new IllegalStateException();
        }
        final NutrientStorage storage = (NutrientStorage) this.energyStorage;
        storage.setNutrientToEnergyConverter(this.geneticCode::getEnergyFromNutrient);
    }

    @Override
    public Energy getEnergy() {
        return this.energyStorage.getEnergyStored();
    }

    @Override
    public boolean isDead() {
        return getEnergy().getAmount() > 0;
    }

    @Override
    public void spendEnergy(final Energy amount) {
        this.energyStorage.takeEnergy(amount);
    }

    @Override
    public void addFood(final Food food) {
        this.energyStorage.storeFood(food);
    }

    @Override
    public Food getInternalFood(final FoodFactory factory) {
        if (this.energyStorage.getClass() != NutrientStorage.class) {
            throw new IllegalStateException();
        }
        final NutrientStorage storage = (NutrientStorage) this.energyStorage;
        return factory.createFoodFromNutrients(storage.getNutrients());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((behavior == null) ? 0 : behavior.hashCode());
        result = prime * result + ((currPerception == null) ? 0 : currPerception.hashCode());
        result = prime * result + ((energyStorage == null) ? 0 : energyStorage.hashCode());
        result = prime * result + ((geneticCode == null) ? 0 : geneticCode.hashCode());
        result = prime * result + ((species == null) ? 0 : species.hashCode());
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
        final BacteriaImpl other = (BacteriaImpl) obj;
        if (behavior == null) {
            if (other.behavior != null) {
                return false;
            }
        } else if (!behavior.equals(other.behavior)) {
            return false;
        }
        if (currPerception == null) {
            if (other.currPerception != null) {
                return false;
            }
        } else if (!currPerception.equals(other.currPerception)) {
            return false;
        }
        if (energyStorage == null) {
            if (other.energyStorage != null) {
                return false;
            }
        } else if (!energyStorage.equals(other.energyStorage)) {
            return false;
        }
        if (geneticCode == null) {
            if (other.geneticCode != null) {
                return false;
            }
        } else if (!geneticCode.equals(other.geneticCode)) {
            return false;
        }
        if (species == null) {
            if (other.species != null) {
                return false;
            }
        } else if (!species.equals(other.species)) {
            return false;
        }
        return true;
    }

}
