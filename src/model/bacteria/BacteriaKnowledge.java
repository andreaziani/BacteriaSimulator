package model.bacteria;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import model.Energy;
import model.action.Action;
import model.food.Nutrient;
import model.perception.Perception;

/**
 * Represent all knowledge a bacteria needs to choose actions. It maintains an
 * action until a new perception is added.
 */
public final class BacteriaKnowledge {
    private Optional<Perception> perception;
    private final Function<Nutrient, Energy> nutrientToEnergyConverter;
    private final Function<Action, Energy> actionCostFunction;
    private final Supplier<Energy> bacteriaEnergy;
    private Optional<Action> action;

    /**
     * Create a new BacteriaKnowledge taking all the informations it stores from the
     * parameters with an empty perception.
     * 
     * @param nutrientToEnergyConverter
     *            a function that gives the gain of Energy for each Nutrient for the
     *            Bacteria.
     * @param actionCostFunction
     *            a function that gives the cost of Energy for each Action for the
     *            Bacteria.
     * @param bacteriaEnergy
     *            the current total Bacteria Energy.
     */
    public BacteriaKnowledge(final Function<Nutrient, Energy> nutrientToEnergyConverter,
            final Function<Action, Energy> actionCostFunction, final Supplier<Energy> bacteriaEnergy) {
        this.nutrientToEnergyConverter = nutrientToEnergyConverter;
        this.actionCostFunction = actionCostFunction;
        this.bacteriaEnergy = bacteriaEnergy;
        action = Optional.empty();
        perception = Optional.empty();
    }

    /**
     * Create a new BacteriaKnowledge taking all the informations it stores from the
     * parameters, included a starting perception.
     * 
     * @param perception
     *            a perception.
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
            final Function<Action, Energy> actionCostFunction, final Supplier<Energy> bacteriaEnergy) {
        this(nutrientToEnergyConverter, actionCostFunction, bacteriaEnergy);
        this.perception = Optional.of(perception);
    }

    /**
     * Set the current perception and reset the current action to empty.
     * 
     * @param perception
     *            a new Perception for the bacteria.
     */
    public void setPerception(final Perception perception) {
        this.perception = Optional.of(perception);
        action = Optional.empty();
    }

    /**
     * @return if the perception has been set for this perception.
     */
    public boolean hasPerception() {
        return perception.isPresent();
    }

    /**
     * @return the current perception this behavior is analyzing.
     * @throws NoSuchElementException
     *             if there is no value present in the perception.
     */
    public Perception getCurrentPerception() {
        return perception.get();
    }

    /**
     * @return the current maximal Energy the bacteria can spend.
     */
    public Energy getBacteriaEnergy() {
        return bacteriaEnergy.get();
    }

    /**
     * @param nutrient
     *            a Nutrient.
     * @return the amount of Energy a Nutrient can give.
     */
    public Energy getNutrientEnergy(final Nutrient nutrient) {
        return this.nutrientToEnergyConverter.apply(nutrient);
    }

    /**
     * @param action
     *            an Action.
     * @return the Energy cost of an action for the current bacteria with this
     *         behavior.
     */
    public Energy getActionCost(final Action action) {
        return this.actionCostFunction.apply(action);
    }

    /**
     * @return An optional containing an action that starts empty.
     */
    public Optional<Action> getAction() {
        return action;
    }

    /**
     * @param action
     *            set the current chosen action.
     */
    public void setAction(final Action action) {
        this.action = Optional.of(action);
    }

    /**
     * @return the speed of the bacteria.
     */
    public double getSpeed() {
        // TODO Auto-generated method stub
        return 0;
    }
}
