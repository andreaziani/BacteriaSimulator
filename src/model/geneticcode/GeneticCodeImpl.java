package model.geneticcode;

import model.Energy;
import model.action.Action;
import model.food.Nutrient;

/**
 * Implementation of interface GeneticCode.
 */

public class GeneticCodeImpl implements GeneticCode {
    private Gene code;
    private SpeedGene speed;
    private NutrientsGene nutrients;
    private ActionsGene actions;
    private double radius;
    private double perceptionRadius;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the code of DNA.
     * @param radius
     *          radius of bacteria.
     * @param perceptionRadius
     *          perception of radius of bacteria.
     */
    public GeneticCodeImpl(final Gene code, final double radius, final double perceptionRadius) {
        this.code = code;
        this.radius = radius;
        this.perceptionRadius = perceptionRadius;
        this.speed = new SpeedGeneImpl(code);
        this.nutrients = new NutrientsGeneImpl(code);
        this.actions = new ActionsGeneImpl(code);
    }

    @Override
    public Gene getCode() {
        return this.code;
    }

    @Override
    public Energy getActionCost(final Action action) {
        return actions.interpretActionCost(action);
    }

    @Override
    public double getSpeed() {
        return speed.interpretSpeed();
    }
    @Override
    public Energy getEnergyFromNutrient(final Nutrient nutrient) {
        return nutrients.interpretNutrients(nutrient);
    }

    @Override
    public Double getRadius() {
        return this.radius;
    }

    @Override
    public Double getPerceptionRadius() {
        return this.perceptionRadius;
    }

}
