package model.bacteria.species.behavior;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;

/**
 * Represents the behavior of a bacteria. It define only the method chooseAction
 * that accepts a perception and return an Action.
 */
@FunctionalInterface
public interface Behavior {
    /**
     * @param knowledge the current knowledge of the bacteria.
     * @return the action preferred from this behavior given the informations it
     *         has.
     */
    Action chooseAction(BacteriaKnowledge knowledge);
}
