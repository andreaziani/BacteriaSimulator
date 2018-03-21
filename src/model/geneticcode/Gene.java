package model.geneticcode;

/**
 * Interface of a GeneticCode type. It represent individual specific
 * characteristic of a Bacteria and can mutate while the bacteria is still
 * alive.
 */

public interface Gene {
    /**
     * Set Code of bacteria's genetic code.
     *
     * @param code
     *            new genetic code.
     */
    void setCode(String code);

    /**
     * DNA code.
     * @return the code of bacteria.
     */
    String getCode();

    /**
     * Gene interprets the DNA code.
     * eg. ""AAA" "AAT" "AAC" "AAG".
     * @return an interpretation of DNA.
     */
    int interpret();
}
