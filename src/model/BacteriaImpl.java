package model;

import java.util.Map;

/**
 * Implementation of interface Bacteria.
 */
public class BacteriaImpl implements Bacteria {

    private Perception currPerception;
    private GeneticCode geneticCode;
    private final Behavior behavior;
    private final EnergyStorage energyStorage;

    /**
     * Construct a Bacteria from a Behavior strategy and a genetic code. This
     * constructor use a nutrientStorage as a default EnergyStorage strategy.
     * 
     * @param specesBehavior
     *            a behavior strategy defining the behavior of this bacteria's
     *            species
     * @param initialGeneticCode
     *            a geneticCode to be inserted in the bacteria.
     */
    public BacteriaImpl(final Behavior specesBehavior, final GeneticCode initialGeneticCode) {
        this.behavior = specesBehavior;
        this.geneticCode = initialGeneticCode;
        this.energyStorage = new NutrientStorage();
    }

    @Override
    public void setPerception(final Perception perception) {
        this.currPerception = perception;
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
        NutrientStorage storage = (NutrientStorage)this.energyStorage;
        return storage.getNutrients();
    }

}
