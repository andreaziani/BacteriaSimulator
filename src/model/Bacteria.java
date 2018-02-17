package model;

public interface Bacteria {
	void setPerception(Perception perception);
	Action getAction();
	GeneticCode getGeneticCode();
	void setGeneticCode(GeneticCode code);
	Energy getEnergy();
	boolean isDead();
	//TODO get/addNutrients
	void spendEnergy(Energy amount);
}
