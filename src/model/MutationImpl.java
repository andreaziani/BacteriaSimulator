package model;

import java.util.Map;

import model.action.Action;
import model.food.Nutrient;

/**
 * 
 * Implementation of Mutation.
 *
 */

public class MutationImpl implements Mutation {

    private String code;
    private final Map<Action, Energy> actions;
    /*
     * Ho inserito anche actions perchè un battere può mutare e decidere di cacciare qualche preda e diventare
     * un Predator, prendendo un target, ovvero dei batteri con un certo codice genetico o direttamente il primo
     * più vicino e fissare il target fino a che non lo divora.
     * es. Action action = Predator;
     * Action action Devour;
     */
    private Map<Nutrient, Energy> nutrients;
    private double speed;
    private GeneticCode geneticCode;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the "name" of modified genetic code.
     * @param actions
     *            a list of possible bacteria's actions.
     * @param nutrients
     *            a list of nutrients of bacteria.
     * @param speed
     *            speed of modified bacteria.
     * @param geneticCode
     *            the code of bacteria to be modified.
     */
    public MutationImpl(final String code, final Map<Action, Energy> actions, final Map<Nutrient, Energy> nutrients, final double speed, final GeneticCode geneticCode) {
        this.code = code;
        this.actions = actions;
        this.nutrients = nutrients;
        this.speed = speed;
        this.geneticCode = geneticCode;
    }


    @Override
    public void alteratedCode(final String code) {
        geneticCode.setCode(code);
    }

    @Override
    public void alteratedActionCost(final Action action, final Energy cost) {
        geneticCode.setActionCost(action, cost);
    }

    @Override
    public void alteratedSpeed(final double speed) {
        geneticCode.setSpeed(speed);
    }

    @Override
    public void alteratedEnergyFromNutrient(final Nutrient nutrient, final Energy cost) {
        geneticCode.setEnergyFromNutrient(nutrient, cost);
    }

}
