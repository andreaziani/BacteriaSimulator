package model.geneticcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import model.Energy;
import model.action.Action;
import model.action.ActionType;
/**
 * Implementation of interface ActionsGene.
 */
public class ActionsGeneImpl implements ActionsGene {

    public Gene code;
    //public static final int var = 15;
    private List<Integer> list;
    //public Map<Integer<Action, Integer>> actions;
    public List<ActionType> actions;
    private static final int VAR_EAT = 3;
    private static final int VAR_REPLICATE = 5;
    private static final int VAR_MOVE = 8;
    private static final int VAR_NOTHING = 1;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public ActionsGeneImpl(final Gene code, final List<ActionType> actions) {
        this.code = code;
        this.actions = actions;
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }
    }
    @SuppressWarnings("unlikely-arg-type")
    @Override
    public Energy interpretActionCost(final Action action) { //TODO chiamato TEMPORANEAMENTE b
        Energy b = null;
        if (action.equals(ActionType.EAT)) {
            b = code.interpret(list, VAR_EAT);
        } else if (action.equals(ActionType.REPLICATE)) {
            b = code.interpret(list, VAR_REPLICATE);
        } else if (action.equals(ActionType.MOVE)) {
            b = code.interpret(list, VAR_MOVE);
        } else if (action.equals(ActionType.NOTHING)) {
            b = code.interpret(list, VAR_NOTHING);
        } else {
            throw new IllegalArgumentException("error");
        }
        return b;
    }
}
