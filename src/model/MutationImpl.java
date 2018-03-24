package model;

import java.util.Map;

import model.action.Action;
import model.food.Nutrient;
import model.geneticcode.Gene;
import model.geneticcode.GeneticCode;

/**
 * 
 * Implementation of Mutation.
 *
 */

public class MutationImpl implements Mutation {

    private Gene code;

    /**
     * Construct a Bacteria's Genetic Code.
     * 
     * @param code
     *            the code of Gene.
     */
    public MutationImpl(final Gene code) {
        this.code = code;
    }

    @Override
    public void alteratedCode(final Gene code) {
        // TODO Auto-generated method stub
    }

}
