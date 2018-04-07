package model.bacteria;

import java.util.Objects;

import model.Energy;
import model.EnergyImpl;
import model.action.Action;
import model.food.Food;
import model.food.FoodFactory;
import model.geneticcode.GeneticCode;
import model.perception.Perception;
import utils.exceptions.MissingPerceptionExeption;

/**
 * Implementation of interface Bacteria.
 */
public class BacteriaImpl implements Bacteria {

    private final int bacteriaId;
    private final GeneticCode geneticCode;
    private final Species species;
    private final EnergyStorage energyStorage;
    private final BacteriaKnowledge knowledge;

    /**
     * Construct a Bacteria from a Behavior strategy and a genetic code. This
     * constructor use a nutrientStorage as a default EnergyStorage strategy.
     * 
     * @param id
     *            a numerical identifier that distinguish each Bacteria present in a
     *            simulation from the others.
     * @param species
     *            this bacteria's species.
     * @param initialGeneticCode
     *            a geneticCode to be inserted in the bacteria.
     * @param startingEnergy
     *            the initial Energy of this bacteria.
     */
    public BacteriaImpl(final int id, final Species species, final GeneticCode initialGeneticCode,
            final Energy startingEnergy) {
        this.bacteriaId = id;
        this.species = species;
        this.geneticCode = initialGeneticCode;
        this.energyStorage = new NutrientStorage(startingEnergy, this.geneticCode::getEnergyFromNutrient);
        knowledge = new BacteriaKnowledge(this.geneticCode::getEnergyFromNutrient, this::getActionCost, this::getEnergy);
    }

    @Override
    public Perception getPerception() {
        return this.knowledge.getCurrentPerception();
    }

    @Override
    public void setPerception(final Perception perception) {
        knowledge.setPerception(perception);
    }

    @Override
    public Species getSpecies() {
        return this.species;
    }

    @Override
    public double getRadius() {
        return this.geneticCode.getRadius();
    }

    @Override
    public double getPerceptionRadius() {
        return this.geneticCode.getPerceptionRadius();
    }

    @Override
    public Action getAction() {
        if (!this.knowledge.hasPerception()) {
            throw new MissingPerceptionExeption();
        } else if (!knowledge.getAction().isPresent()) {
            knowledge.setAction(this.species.getBehavior().chooseAction(knowledge));
        }
        return knowledge.getAction().get();
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
        return getEnergy().compareTo(EnergyImpl.ZERO) <= 0;
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
        return Objects.hash(bacteriaId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BacteriaImpl other = (BacteriaImpl) obj;
        return Objects.equals(this.bacteriaId, other.bacteriaId);
    }

    @Override
    public String toString() {
        return "Bacteria: [Specie = " + this.species.getName() + ", Energy = " + this.getEnergy().toString() + "]";
    }

}
