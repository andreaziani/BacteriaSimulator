package model.geneticcode;

import java.util.List;
import java.util.stream.IntStream;
/**
 * Implementation of interface ActionsGene.
 */
public class ActionsGeneImpl implements ActionsGene {

    public Gene code;
    public static final int var = 15;
    private List<Integer> list;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public ActionsGeneImpl(final Gene code) {
        this.code = code;
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }
    }
    @Override
    public int interpretActionCost() { //TODO chiamato TEMPORANEAMENTE b
        int b = code.interpret(list);
        return b;
    }
}
