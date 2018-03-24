package model.geneticcode;

import java.util.List;

import model.Energy;

/**
 * Interface of a GeneticCode type. It represent individual specific
 * characteristic of a Bacteria and can mutate while the bacteria is still
 * alive.
 */

public interface Gene {
    /**
     * DNA code.
     * @return the code of bacteria.
     */
    List<NucleicAcid> getCode();

    /**
     * Gene interprets part of the DNA code.
     * eg. ""AAA" "AAT" "AAC" "AAG".
     * @param list
     *          sector of DNA to interpret.
     * @param var
     *          variable to divide energy.
     * @return an interpretation of DNA.
     */
    Energy interpret(List<Integer> list, int var);
}
