package model.geneticcode;

/**
 * 
 * Interface of CopyFactory. Create an identical Bacteria with the Gene.
 *
 */
public interface CopyFactory {
    /**
     * Replicate a bacteria.
     * @param gc
     *          the code of Genetic Code.
     * @return a bacteria with genetic code.
     */
    GeneticCode copyGene(GeneticCode gc);
}
