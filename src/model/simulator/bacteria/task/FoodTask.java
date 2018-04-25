package model.simulator.bacteria.task;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.bacteria.ActionPerformer;
import model.state.Position;

/**
 * 
 * @author fedemengo
 *
 */
public abstract class FoodTask extends Task {

    public FoodTask(Position position, Bacteria bacteria, final ActionPerformer performer, final Energy cost) {
        super(position, bacteria, performer, cost);
    }
}
