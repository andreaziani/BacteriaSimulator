package model;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.food.Nutrient;

/**
 * Implementation of interface GeneticCode.
 */

public class GeneticCodeImpl implements GeneticCode {

    public String code;
    public Map<Action, Energy> actions;
    public Map<Nutrient, Energy> nutrients;
    public double speed;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the "name" of genetic code.
     * @param actions
     *            a list of possible bacteria's actions.
     * @param speed
     *            speed of bacteria.
     */
    public GeneticCodeImpl(final String code, final Map<Action, Energy> actions, final double speed) {
        this.code = code;
        this.actions = new HashMap<>();
        this.speed = speed;
    }

	@Override
	public void setActionCost(final Action action, final Energy cost) {
		if (!this.actions.containsKey(action)) {
			throw new IllegalArgumentException();
		}
		else {
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
		}
		else {
			return (Energy) actions.get(action);
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
		}
		else {
			return (Energy) nutrients.get(nutrient);
		}
    }

	@Override
	public void setEnergyFromNutrient(final Nutrient nutrient, final Energy cost) {
		if (!this.nutrients.containsKey(nutrient)) {
			throw new IllegalArgumentException();
		}
		else {
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
