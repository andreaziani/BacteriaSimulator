package model;

import model.geneticcode.Gene;
import model.geneticcode.NucleicAcid;

/**
 * Interface of a classical mutation of a bacteria. It alters many
 * characteristics of a Bacteria.
 */
public interface Mutation {
    /**
     * Alter the code of Genetic Code.
     */
    void alteratedCode();
}
