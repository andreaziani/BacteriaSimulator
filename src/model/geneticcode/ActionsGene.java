package model.geneticcode;

import model.Energy;
import model.action.Action;

/**
 * Interface of a part of GeneticCode. It represent the speed of bacteria.
 */
public interface ActionsGene {
    /**
     * Gene interprets part of the DNA code.
     * eg. ""AAA" "AAT" "AAC" "AAG".
     * @return an interpretation of DNA.
     */
    Energy interpretActionCost();
/*
    /**
     * Gene take a interpretation of DNA and transforms in energy.
     * @param action
     *          action of bacteria.
     */
    //void actionsInterpreted(Action action);
}
