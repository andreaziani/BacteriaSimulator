package model;

import java.util.Map;

import model.action.Action;
import model.food.Nutrient;

/**
 * Implementation of interface GeneticCode.
 */

public class GeneticCodeImpl implements GeneticCode {
    private String code;
    private final Map<Action, Energy> actions;
    private Map<Nutrient, Energy> nutrients;
    private double speed;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the "name" of genetic code.
     * @param actions
     *            a list of possible bacteria's actions.
     * @param nutrients
     *            a list of nutrients of bacteria.
     * @param speed
     *            speed of bacteria.
     */
    public GeneticCodeImpl(final String code, final Map<Action, Energy> actions, final Map<Nutrient, Energy> nutrients, final double speed) {
        this.code = code;
        this.actions = actions;
        this.nutrients = nutrients;
        this.speed = speed;
    }

    @Override
    public void setActionCost(final Action action, final Energy cost) {
        if (!this.actions.containsKey(action)) {
            throw new IllegalArgumentException();
        } else {
            actions.put(action, cost);
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public Energy getActionCost(final Action action) {
        if (!this.actions.containsKey(action)) {
            throw new IllegalArgumentException();
        } else {
            return actions.get(action);
        }
    }

    @Override
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public Energy getEnergyFromNutrient(final Nutrient nutrient) {
        if (!this.nutrients.containsKey(nutrient)) {
            throw new IllegalArgumentException();
        } else {
            return nutrients.get(nutrient);
        }
    }

    @Override
    public void setEnergyFromNutrient(final Nutrient nutrient, final Energy cost) {
        if (!this.nutrients.containsKey(nutrient)) {
            throw new IllegalArgumentException();
        } else {
            nutrients.put(nutrient, cost);
        }
    }

    @Override
    public Double getRadius() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getPerceptionRadius() {
        // TODO Auto-generated method stub
        return null;
    }

}
