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
    private static final int FIRST = 10;
    private static final int SECOND = 5;
    private static final int THIRD = 7;

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

    @Override
    public final List<NucleicAcid> getCode() {
        return this.code;
    }

    private double singleNaCode(final List<Integer> list, final int pos) {
        return this.getCode().get(list.get(pos)).ordinal();
    }

    @Override
    public final Double interpret(final List<Integer> list, final int var) {
        return ((singleNaCode(list, 0) * FIRST) + (singleNaCode(list, 1) * SECOND) + (singleNaCode(list, 2) * THIRD)) % var;
    }

}
