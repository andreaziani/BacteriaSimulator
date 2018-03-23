    package model.geneticcode;

import java.util.List;

import model.Energy;

/**
 * Implementation of interface Gene.
 */
public class GeneImpl implements Gene {

    public List<NucleicAcid> code;
    public static final int var = 15;
    /**
     * Construct a Gene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public GeneImpl(final List<NucleicAcid> code) {
        this.code = code;
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

    @Override
    public Energy interpret(final List<Integer> list) {
        Energy b = (this.getCode().get(list.get(0)).ordinal() + this.getCode().get(list.get(1)).ordinal() + this.getCode().get(list.get(2)).ordinal()) % var;
        return b;
    }

}
