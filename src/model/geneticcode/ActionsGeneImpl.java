package model.geneticcode;

import java.util.ArrayList;
import java.util.List;
import model.Energy;
import model.EnergyImpl;
import model.action.Action;
import model.action.ActionType;
/**
 * Implementation of interface ActionsGene.
 */
public class ActionsGeneImpl implements ActionsGene {

    private final Gene code;
    private final List<Integer> list;
    private static final int VAR_EAT = 3;
    private static final int VAR_REPLICATE = 50;
    private static final int VAR_MOVE = 5;
    private static final int VAR_NOTHING = 1;
    private static final int MIN_ZONE = 1;
    private static final int MAX_ZONE = 4;
    private double en;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public ActionsGeneImpl(final Gene code) {
        this.code = code;
        this.list = new ArrayList<>();
        for (int i = MIN_ZONE; i < MAX_ZONE; i++) {
            list.add(i);
        }
    }
    @Override
    public Energy interpretActionCost(final Action action) {
        if (action.getType().equals(ActionType.EAT)) {
            this.en = code.interpret(list, VAR_EAT);
        } else if (action.getType().equals(ActionType.REPLICATE)) {
            this.en = code.interpret(list, VAR_REPLICATE);
        } else if (action.getType().equals(ActionType.MOVE)) {
            this.en = code.interpret(list, VAR_MOVE);
        } else if (action.getType().equals(ActionType.NOTHING)) {
            this.en = code.interpret(list, VAR_NOTHING);
        } else {
            throw new IllegalArgumentException("error type of action");
        }
        return new EnergyImpl(en);
    }
}
