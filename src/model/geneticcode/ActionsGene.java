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
     * @param action
     *          actions of bacteria.
     * @return an interpretation of DNA.
     */
    Energy interpretActionCost(Action action);
}
