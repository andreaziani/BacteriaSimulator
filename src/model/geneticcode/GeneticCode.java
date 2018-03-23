package model.geneticcode;

import model.Energy;
import model.action.Action;
import model.food.Nutrient;

/**
 * Interface of a GeneticCode type. It represent individual specific
 * characteristic of a Bacteria and can mutate while the bacteria is still
 * alive.
 */
public interface GeneticCode extends Cloneable {
    /**
     * Get the Energy cost for executing an Action, depends on the action but also
     * on the particular GeneticCode.
     * 
     * @param action
     *            an action
     * @return the cost of executing that action with this GeneticCode.
     */
    Energy getActionCost(Action action);

    /**
     * @return the speed of a bacteria with this GeneticCode.
     */
    double getSpeed();

    /**
     * 
     * @return the code of bacteria.
     */
    Gene getCode();

    /**
     * Get the amount of Energy a Nutrient can provide to a Bacteria with this
     * GenetiCode.
     * 
     * @param nutrient
     *            a Nutrient.
     * @return the amount of Energy that the Bacteria can gain by eating the given
     *         Nutrient, it is negative if the Nutrient is nocive.
     */
    Energy getEnergyFromNutrient(Nutrient nutrient);

    /**
     * @return the radius of a bacteria with this GeneticCode.
     */
    Double getRadius();

    /**
     * @return the radius of the perception of a bacteria with this GeneticCode.
     */
    Double getPerceptionRadius();

    /**
     * @return a new GeneticCode object that is a copy of the current GeneticCode
     *          this == this.clone() result is false
     *          this.equals(this.clone()) result is true
     */
    GeneticCode clone();
}
