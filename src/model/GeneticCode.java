package model;

public interface GeneticCode {
	Energy getActionCost(Action action);
	double getSpeed();
	Energy getEnergyFromFood(Food food);
}
