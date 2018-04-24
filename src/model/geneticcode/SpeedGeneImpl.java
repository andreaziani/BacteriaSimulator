package model.geneticcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of interface SpeedGene.
 */
public class SpeedGeneImpl implements SpeedGene {

    private final Gene code;
    private static final int VAR = 10;
    private final List<Integer> list;
    private static final int MIN_ZONE = 4;
    private static final int MAX_ZONE = 7;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public SpeedGeneImpl(final Gene code) {
        this.code = code;
        this.list = new ArrayList<>();
        for (int i = MIN_ZONE; i < MAX_ZONE; i++) {
            this.list.add(i);
        }
    }

    @Override
    public final double interpretSpeed() {
        return code.interpret(list, VAR);
    }
}
