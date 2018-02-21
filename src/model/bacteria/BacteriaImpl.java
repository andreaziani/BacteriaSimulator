package model.bacteria;

import java.util.Map;

import model.Energy;
import model.GeneticCode;
import model.action.Action;
import model.food.Food;
import model.food.Nutrient;
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
     */
    public BacteriaImpl(final Species species, final GeneticCode initialGeneticCode) {
        this.species = species;
        this.behavior = species.getBehavior();
        this.geneticCode = initialGeneticCode;
        this.energyStorage = new NutrientStorage(this.geneticCode::getEnergyFromNutrient);
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
    public Action getAction() {
        return this.behavior.chooseAction(this.currPerception);
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
    public Map<Nutrient, Double> getNutrients() {
        if (this.energyStorage.getClass() != NutrientStorage.class) {
            throw new IllegalStateException();
        }
        final NutrientStorage storage = (NutrientStorage) this.energyStorage;
        return storage.getNutrients();
    }
}
