package model.geneticcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Energy;
import model.action.Action;
import model.food.Nutrient;

/**
 * Implementation of interface GeneticCode.
 */

public class GeneticCodeImpl implements GeneticCode {
    public List<NucleicAcid> code = new ArrayList<NucleicAcid>();
    public SpeedGene speed;
    public NutrientsGene nutrients;
    public ActionsGene actions;
    public double radius;
    public double perceptionRadius;
    public static final int DNAnumber = 12;

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
    public GeneticCodeImpl(final List<NucleicAcid> code, final double radius, final double perceptionRadius) {
        this.code = code;
        this.radius = radius;
        this.perceptionRadius = perceptionRadius;
    }

    /**
     * Construct a Bacteria's Genetic Code.
     */
    public GeneticCodeImpl() {
        for (int i = 0; i < DNAnumber; i++) {
            Random rand = new Random();
            int rnd = rand.nextInt(NucleicAcid.values().length);
            this.code.add(NucleicAcid.values()[rnd]);
        }
    }

    @Override
    public Gene getCode() {
        return (Gene) this.code;
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
/*
    @Override
    public GeneticCode clone() {
        final ActionsGene clonedActions = 
        final Map<Nutrient, Energy> clonedNutrients = new HashMap<>(this.nutrients);
        return new GeneticCodeImpl((Gene) this.code.getCode(), clonedActions, clonedNutrients, this.speed, this.radius, this.perceptionRadius);
    }*/

}
