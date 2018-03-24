package model.geneticcode;

import java.util.ArrayList;
import java.util.List;

import model.Energy;
import model.EnergyImpl;

/**
 * Implementation of interface SpeedGene.
 */
public class SpeedGeneImpl implements SpeedGene {

    private Gene code;
    private static final int VAR = 10;
    private List<Integer> list;
    private double speed;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public SpeedGeneImpl(final Gene code) {
        this.code = code;
        this.list = new ArrayList<>();
        for (int i = 4; i < 7; i++) {
            this.list.add(i);
        }
    }

    @Override
    public double interpretSpeed() {
        this.speed = code.interpret(list, VAR);
        return speed;
    }
}
