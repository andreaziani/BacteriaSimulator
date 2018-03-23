package model.geneticcode;

import java.util.List;

import model.Energy;

/**
 * Implementation of interface NutrientsGene.
 */
public class NutrientsGeneImpl implements NutrientsGene {

    public Gene code;
    public static final int var = 12;
    private List<Integer> list;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public NutrientsGeneImpl(final Gene code) {
        this.code = code;
        for (int i = 7; i < 10; i++) {
            list.add(i);
        }
    }

    @Override
    public Energy interpretNutrients() {
        Energy b = code.interpret(list);
        return b;
    }
}
