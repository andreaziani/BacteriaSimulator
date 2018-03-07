package model;

import model.action.Action;
import model.food.Nutrient;

/**
 * Interface of a classical mutation of a bacteria. It alters many
 * characteristics of a Bacteria.
 */
public interface Mutation {
    /**
     * Alter the code of Genetic Code.
     * @param code
     *           alteration of fisic's genetic code.
     */
    void alteratedCode(String code);

    /**
     * Alter cost of bacteria's actions.
     * @param action
     *           an action of Bacteria.
     * @param cost
     *           the new cost of a Bacteria's action.
     */
    void alteratedActionCost(Action action, Energy cost);

    /**
     * Alter the speed of Bacteria.
     * @param speed
     *         the alteration speed of Bacteria.
     */
    void alteratedSpeed(double speed);

    /** Modify the energy from nutrient.
     * @param nutrient
     *         food eaten by bacteria.
     * @param cost
     *         energy produced by the nutrient.
     */
    void alteratedEnergyFromNutrient(Nutrient nutrient, Energy cost);
}
