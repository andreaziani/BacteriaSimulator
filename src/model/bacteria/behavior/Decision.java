package model.bacteria.behavior;

import model.action.Action;

/**
 * Represent a decision of an Action.
 */
public interface Decision {
    /**
     * @return the action chosen by this decision.
     */
    Action getAction();
    /**
     * @return a degree of confidence of the decision in the range [0, 1].
     */
    double getConfidence();
}
