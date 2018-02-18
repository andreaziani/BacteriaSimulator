package model;

/**
 * Represents the behavior of a bacteria. It define only the method chooseAction
 * that accepts a perception and return an Action.
 */
@FunctionalInterface
public interface Behavior {
    /**
     * @param perception
     *            a perception from which to decide what to do.
     * @return the action preferred from this behavior given the informations it
     *         has.
     */
    Action chooseAction(Perception perception);
}
