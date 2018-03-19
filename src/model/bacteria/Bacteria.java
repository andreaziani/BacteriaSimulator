package model.bacteria;

import model.Energy;
import model.GeneticCode;
import model.action.Action;
import model.food.Food;
import model.food.FoodFactory;
import model.perception.Perception;
import model.simulator.Collidable;

/**
 * The interface representing a bacteria in the simulation.
 */
public interface Bacteria extends Collidable {
    /**
     * @param perception
     *            a perception that will be set to be the current perception of the
     *            bacteria.
     */
    void setPerception(Perception perception);

    /**
     * @return the current perception of the bacteria.
     */
    Perception getPerception();

    /**
     * @return an action the bacteria will choose given the current perception it
     *         has.
     */
    Action getAction();

    /**
     * @return the species of this bacteria.
     */
    Species getSpecies();

    /**
     * @return the radius of perception of this bacteria.
     */
    Double getPerceptionRadius();

    /**
     * Get the Energy cost for executing an Action by this Bacteria.
     * 
     * @param action
     *            an Action
     * @return the cost of executing that Action by this Bacteria.
     */
    Energy getActionCost(Action action);

    /**
     * @return the genetic code of the bacteria.
     */
    GeneticCode getGeneticCode();

    /**
     * @return the amount of energy this bacteria currently have as a reserve.
     */
    Energy getEnergy();

    /**
     * @return if the bacteria is no more able to act.
     */
    boolean isDead();

    /**
     * @param food
     *            a food the bacteria has eaten.
     */
    void addFood(Food food);

    /**
     * Create a Food from a bacteria, takes a FoodFactory as a strategy to create a
     * Food from the bacteria nutrients.
     * 
     * @param factory
     *            a FoodFactory from to create a food from the bacteria.
     * @return a new food, representing the nutrients not consumed by the bacteria.
     * @throws IllegalStateExeption
     *             if this bacteria does not contain nutrients.
     */
    Food getInternalFood(FoodFactory factory);

    /**
     * @param amount
     *            an amount of energy that the bacteria must spend from his reserve.
     * @throws NotEnounghEnergyException
     *             if the bacteria would have less that 0 energy after spending the
     *             given amount.
     */
    void spendEnergy(Energy amount);
}
