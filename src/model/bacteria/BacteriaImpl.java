package model.bacteria;

import java.util.Objects;

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
    private final GeneticCode geneticCode;
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
    public double getPerceptionRadius() {
        return this.geneticCode.getPerceptionRadius();
    }

    @Override
    public Action getAction() {
        return this.behavior.chooseAction(new BacteriaKnowledge(this.currPerception,
                this.geneticCode::getEnergyFromNutrient, this::getActionCost, this.getEnergy()));
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
    public double getSpeed() {
        return this.geneticCode.getSpeed();
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
        return Objects.hash(currPerception, energyStorage, geneticCode, species);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BacteriaImpl other = (BacteriaImpl) obj;
        return Objects.equals(this.currPerception, other.currPerception)
                && Objects.equals(this.energyStorage, other.energyStorage)
                && Objects.equals(this.geneticCode, other.geneticCode) && Objects.equals(this.species, other.species);
    }

}
