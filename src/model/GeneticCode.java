package model;

import model.food.Nutrient;

/**
 * Interface of a GeneticCode type. It represent individual specific
 * characteristic of a Bacteria and can mutate while the bacteria is still
 * alive.
 */
public interface GeneticCode {
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
     * Get the amount of Energy a Nutrient can provide to a Bacteria with this
     * GenetiCode.
     * 
     * @param nutrient
     *            a Nutrient.
     * @return the amount of Energy that the Bacteria can gain by eating the given
     *         Nutrient, it is negative if the Nutrient is nocive.
     */
    Energy getEnergyFromNutrient(Nutrient nutrient);
}
