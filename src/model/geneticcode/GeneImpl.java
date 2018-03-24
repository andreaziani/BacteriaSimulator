    package model.geneticcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Energy;
import model.EnergyImpl;

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
            Random rand = new Random();
            int rnd = rand.nextInt(NucleicAcid.values().length);
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

    @Override
    public Double interpret(final List<Integer> list, final int var) {
        double a = (this.getCode().get(list.get(0)).ordinal() + this.getCode().get(list.get(1)).ordinal() + this.getCode().get(list.get(2)).ordinal()) % var;
        return a;
    }

}
