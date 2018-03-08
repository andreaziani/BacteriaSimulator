package model.bacteria.behavior;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import model.Energy;
import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.food.Nutrient;
import model.perception.Perception;

/**
 * Abstract implementation of a Behavior that evaluates some actions, assign a
 * score to each of them and choose the one with the best score.
 */
public abstract class AbstractDecisionBehavior implements Behavior {

    private final Map<Action, Double> decisions;
    private Perception perception;
    private Function<Nutrient, Energy> nutrientToEnergyConverter;
    private Function<Action, Energy> actionCostFunction;

    /**
     * Create an abstractDecisionBehavior.
     */
    public AbstractDecisionBehavior() {
        this.decisions = new HashMap<>();
    }

    /**
     * @return the decisions. This is not a copy or an unmodifiable set, it is
     *         intended to be modified.
     */
    protected final Map<Action, Double> getDecisions() {
        return decisions;
    }

    /**
     * @return the current perception this behavior is analyzing.
     */
    protected final Perception getCurrentPerception() {
        return perception;
    }

    /**
     * @param nutrient
     *            a Nutrient.
     * @return the amount of Energy a Nutrient can give.
     */
    protected final Energy getNutrientEnergy(final Nutrient nutrient) {
        return this.nutrientToEnergyConverter.apply(nutrient);
    }

    /**
     * @param action
     *            an Action.
     * @return the Energy cost of an action for the current bacteria with this
     *         behavior.
     */
    protected final Energy getActionCost(final Action action) {
        return this.actionCostFunction.apply(action);
    }

    /**
     * Search the decisions of the behavior and set to zero the value of all actions
     * that satisfy the Predicate.
     * 
     * @param cond
     *            a condition for each Action.
     */
    protected void cleanActionDecisions(final Predicate<Action> cond) {
        this.getDecisions().forEach((a, b) -> {
            if (cond.test(a)) {
                getDecisions().put(a, 0.0);
            }
        });
    }

    /**
     * This method must be used internally to modify the decisions, which can be
     * accessed using getDecisions, while the perception can be accessed using
     * getCurrentPerception. This method is the only way for an extension of this
     * class to make a decision about the Action to choose.
     */
    protected abstract void updateDecisions();

    @Override
    public final Action chooseAction(final Perception perception,
            final Function<Nutrient, Energy> nutrientToEnergyConverter,
            final Function<Action, Energy> actionCostFunction) {
        decisions.clear();
        this.perception = perception;
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
        this.actionCostFunction = actionCostFunction;

        updateDecisions();
        return decisions.keySet().stream().max((a1, a2) -> (int) (decisions.get(a1) - decisions.get(a2)))
                .orElseGet(() -> new SimpleAction(ActionType.NOTHING));
    }
}
