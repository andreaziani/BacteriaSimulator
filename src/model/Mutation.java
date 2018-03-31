package model;

/**
 * Interface of a classical mutation of a bacteria. It alters many
 * characteristics of a Bacteria.
 */
public interface Mutation {
    /**
     * Alter the code of Genetic Code.
     */
    void alteratedCode();

    /**
     * the state of mutation of bacteria.
     * @return the state of mutation: true if bacteria mutated, else false.
     */
    boolean isMutated();
}
