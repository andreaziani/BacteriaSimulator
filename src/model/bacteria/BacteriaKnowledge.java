package model.bacteria;

import java.util.function.Function;

import model.Energy;
import model.action.Action;
import model.food.Nutrient;
import model.perception.Perception;

/**
 * Represent all knowledge a bacteria needs to choose actions.
 */
public class BacteriaKnowledge {
    private final Perception perception;
    private final Function<Nutrient, Energy> nutrientToEnergyConverter;
    private final Function<Action, Energy> actionCostFunction;
    private final Energy bacteriaEnergy;

    /**
     * Create a new BacteriaKnowledge taking all the informations it stores from the
     * parameters.
     * 
     * @param perception
     *            the current Bacteria Perception.
     * @param nutrientToEnergyConverter
     *            a function that gives the gain of Energy for each Nutrient for the
     *            Bacteria.
     * @param actionCostFunction
     *            a function that gives the cost of Energy for each Action for the
     *            Bacteria.
     * @param bacteriaEnergy
     *            the current total Bacteria Energy.
     */
    public BacteriaKnowledge(final Perception perception, final Function<Nutrient, Energy> nutrientToEnergyConverter,
            final Function<Action, Energy> actionCostFunction, final Energy bacteriaEnergy) {
        this.perception = perception;
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
        this.actionCostFunction = actionCostFunction;
        this.bacteriaEnergy = bacteriaEnergy;
    }

    /**
     * @return the current perception this behavior is analyzing.
     */
    public final Perception getCurrentPerception() {
        return perception;
    }

    /**
     * @return the current maximal Energy the bacteria can spend.
     */
    public final Energy getBacteriaEnergy() {
        return bacteriaEnergy;
    }

    /**
     * @param nutrient
     *            a Nutrient.
     * @return the amount of Energy a Nutrient can give.
     */
    public final Energy getNutrientEnergy(final Nutrient nutrient) {
        return this.nutrientToEnergyConverter.apply(nutrient);
    }

    /**
     * @param action
     *            an Action.
     * @return the Energy cost of an action for the current bacteria with this
     *         behavior.
     */
    public final Energy getActionCost(final Action action) {
        return this.actionCostFunction.apply(action);
    }
}
