package model;

import model.geneticcode.Gene;

/**
 * Interface of a classical mutation of a bacteria. It alters many
 * characteristics of a Bacteria.
 */
public interface Mutation {
    /**
     * Alter the code of Genetic Code.
     * @param code
     *           alteration of fisic's genetic code.
     */
    void alteratedCode(Gene code);
}
