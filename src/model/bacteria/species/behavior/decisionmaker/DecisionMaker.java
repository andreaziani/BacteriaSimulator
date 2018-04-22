package model.bacteria.species.behavior.decisionmaker;

import java.util.Map;

import model.action.Action;
import model.bacteria.BacteriaKnowledge;

/**
 * Represents an object that support decision making of a Behavior by taking a
 * BacteriaKnowledge object and returning and association between some actions
 * and a score.
 */
@FunctionalInterface
public interface DecisionMaker {
    /**
     * Takes the knowledge of a bacteria and outputs a map of Actions associated to
     * double scores assigned by this DecisionMaker.
     * 
     * @param knowledge
     *            the knowledge of the bacteria making decisions.
     * @return a new Map representing the preferences of this DecisionMaker.
     */
    Map<Action, Double> getDecision(BacteriaKnowledge knowledge);
}
