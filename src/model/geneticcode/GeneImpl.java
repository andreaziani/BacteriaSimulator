package model.geneticcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of interface Gene.
 */
public class GeneImpl implements Gene {

    private List<NucleicAcid> code = new ArrayList<>();
    private static final int DNA_NUMBER = 12;

    /**
     * Construct a Gene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public GeneImpl(final List<NucleicAcid> code) {
        this.code = code;
    }
    /**
     * Constructor empty for Gene.
     */
    public GeneImpl() {
        for (int i = 0; i < DNA_NUMBER; i++) {
            final Random rand = new Random();
            final int rnd = rand.nextInt(NucleicAcid.values().length);
            this.code.add(NucleicAcid.values()[rnd]);
        }
    }

    /*TODO definire che le caratteristiche riguardano A T C G e solo quelle.
     * Poichè il codice genetico è basato solo su quello.
     * es. codice genetico "G-AAA-TAC-CGA"
     * 1° considera se muta oppure no. booelan 
     * 2° interpreta il costo delle azioni.
     * 3° interpreta la speed.
     * 4° interpreta l'energia dei nutrienti.
     */

    @Override
    public List<NucleicAcid> getCode() {
        return this.code;
    }

    private double singleNaCode(final List<Integer> list, final int pos) {
        return this.getCode().get(list.get(pos)).ordinal();
    }

    @Override
    public Double interpret(final List<Integer> list, final int var) {
        return (singleNaCode(list, 0) + singleNaCode(list, 1) + singleNaCode(list, 2)) % var;
    }

}
