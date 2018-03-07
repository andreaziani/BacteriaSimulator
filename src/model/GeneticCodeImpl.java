package model;

import java.util.HashMap;
import java.util.Map;

import model.action.Action;
import model.food.Nutrient;

public class GeneticCodeImpl implements GeneticCode {
    
	public String code;
	public Map<Action, Energy> actions;
	public double speed;
	
	//inserire eccezioni!!
	public GeneticCodeImpl(final String code, Map<Action, Energy> actions, final double speed){
		this.code = code;
		this.actions = new HashMap<>();
		this.speed = speed;
	}
	 
	@Override
	public void setActionCost(Action action, Energy cost){
		 actions.put(action, cost);
	 }
	 
	@Override
	public String getCode(){
		 return code;
	 }
	 
	@Override
    public Energy getActionCost(Action action){
		return (Energy) actions.get(action);
	}//MOLTO BRUTTO. GET NON DOVREBBE PRENDERE IN INPUT NIENTE
    
    @Override
    public double getSpeed(){
		return speed;
	}
    
    @Override
	public Energy getEnergyFromNutrient(Nutrient nutrient) {
    	
    }

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
		
	}

}
