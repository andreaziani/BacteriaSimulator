package model.bacteria.behavior;

import java.util.function.Function;

import model.Energy;
import model.action.Action;
import model.food.Nutrient;
import model.perception.Perception;

/**
 * Represents the behavior of a bacteria. It define only the method chooseAction
 * that accepts a perception and return an Action.
 */
@FunctionalInterface
public interface Behavior {
    /**
     * @param perception
     *            a perception from which to decide what to do.
     * @param nutrientToEnergyConverter
     *            a function that associates nutrients and energy.
     * @return the action preferred from this behavior given the informations it
     *         has.
     */
    Action chooseAction(Perception perception, Function<Nutrient, Energy> nutrientToEnergyConverter);
}
