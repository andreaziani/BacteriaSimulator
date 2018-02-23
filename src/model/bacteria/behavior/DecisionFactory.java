package model.bacteria.behavior;

import model.Direction;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalActionImpl;
import model.action.SimpleAction;

/**
 * Static factory for creating Decision objects from ActionType values.
 */
public class DecisionFactory {
    private static class DecisionImpl implements Decision {

        private final Action action;
        private final double confidence;

        /**
         * Create a decision from an Action and a value of confidence.
         * 
         * @param action
         *            an Action.
         * @param confidence
         *            a confidence value.
         */
        private DecisionImpl(final Action action, final double confidence) {
            this.action = action;
            this.confidence = confidence;
        }

        @Override
        public Action getAction() {
            return this.action;
        }

        @Override
        public double getConfidence() {
            return this.confidence;
        }
    }

    private DecisionFactory() {
    }

    /**
     * Create a Decision of a DirectionalAction with a given confidence.
     * 
     * @param type
     *            an ActionType representing a DirectionalAction.
     * @param dir
     *            the direction of the Action in this Decision.
     * @param confidence
     *            the confidence for this Decision.
     * @return a new Decision with a DirectionalAction configured by the parameters.
     * @throws IllegalArgumentExeption
     *             if type is not Move
     */
    public static Decision directionalDecision(final ActionType type, final Direction dir, final double confidence) {
        return new DecisionImpl(new DirectionalActionImpl(type, dir), confidence);
    }

    /**
     * Create a Decision of a SimpleAction with a given confidence.
     * 
     * @param type
     *            an ActionType representing a SimpleAction.
     * @param confidence
     *            the confidence for this Decision.
     * @return a new Decision with a SimpleAction configured by the parameters.
     * @throws IllegalArgumentExeption
     *             if type is not Eat, Nothing or Replicate
     */
    public static Decision simpleDecision(final ActionType type, final double confidence) {
        return new DecisionImpl(new SimpleAction(type), confidence);
    }
}
