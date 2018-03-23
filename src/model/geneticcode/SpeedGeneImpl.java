package model.geneticcode;

import java.util.List;

import model.Energy;

/**
 * Implementation of interface SpeedGene.
 */
public class SpeedGeneImpl implements SpeedGene {

    public Gene code;
    public static final int var = 10;
    private List<Integer> list;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public SpeedGeneImpl(final Gene code) {
        this.code = code;
        for (int i = 4; i < 7; i++) {
            list.add(i);
        }
    }

    @Override
    public Energy interpretSpeed() {
        Energy b = code.interpret(list);
        return b;
    }
}
