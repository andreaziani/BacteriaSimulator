package model;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.food.Nutrient;

public class GeneticCodeImpl implements GeneticCode {
    
	public String code;
	public Map<Action, Energy> actions;
	public Map<Nutrient, Energy> nutrients;
	public double speed;
	
	//insert exceptions!!
	public GeneticCodeImpl(final String code, Map<Action, Energy> actions, final double speed){
		this.code = code;
		this.actions = new HashMap<>();
		this.speed = speed;
	}

	@Override
	public void setActionCost(Action action, Energy cost){
		if(!this.actions.containsKey(action)) {
			throw new IllegalArgumentException();
		}
		else {
			actions.put(action, cost);
		}
	 }

	@Override
	public String getCode(){
		 return code;
	 }

	@Override
    public Energy getActionCost(Action action){
		if(!this.actions.containsKey(action)) {
			throw new IllegalArgumentException();
		}
		else {
			return (Energy) actions.get(action);
		}
	}//MOLTO BRUTTO. GET NON DOVREBBE PRENDERE IN INPUT NIENTE
    
    @Override
    public double getSpeed(){
		return speed;
	}
    
    
    
    @Override
	public Energy getEnergyFromNutrient(Nutrient nutrient) {
    	if(!this.nutrients.containsKey(nutrient)) {
			throw new IllegalArgumentException();
		}
		else {
			return (Energy) nutrients.get(nutrient);
		}
    }

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public Energy setEnergyFromNutrient(Nutrient nutrient, Energy cost) {
		if(!this.nutrients.containsKey(nutrient)) {
			throw new IllegalArgumentException();
		}
		else {
			return nutrients.put(nutrient, cost);
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
