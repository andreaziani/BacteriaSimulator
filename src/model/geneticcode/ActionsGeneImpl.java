package model.geneticcode;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import model.Energy;
import model.action.Action;
/**
 * Implementation of interface ActionsGene.
 */
public class ActionsGeneImpl implements ActionsGene {

    public Gene code;
    public static final int var = 15;
    private List<Integer> list;
    public Map<Action, Integer> actions;
    /**
     * Construct a SpeedGene of GeneticCode.
     * 
     * @param code
     *          the code of DNA.
     */
    public ActionsGeneImpl(final Gene code, Map<Action, Integer> actions) {
        this.code = code;
        this.actions = actions;
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }
    }
    @Override
    public Energy interpretActionCost() { //TODO chiamato TEMPORANEAMENTE b
        Energy b = code.interpret(list);
        return b;
    }
/*
    public void actionsInterpreted(final Action action) {
        Random rand = new Random();
        int rnd = rand.nextInt(5); //e se devo contare anche lo 0? diviso 0 non si può fare.
        if (!this.actions.containsKey(action)) {
            throw new IllegalArgumentException();
        }
        else {
            actions.put(action, (interpretActionCost() / rnd));
        }
    }*/
}
