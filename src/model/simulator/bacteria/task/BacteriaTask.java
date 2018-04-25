package model.simulator.bacteria.task;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.bacteria.ActionPerformer;
import model.state.Position;

/**
 * Task that interact with the bacteria environment.
 *
 */
public abstract class BacteriaTask extends TaskBase {

    /**
     * Construct a BacteriaTask.
     * @param position the position of the bacteria
     * @param bacteria the bacteria that has to perform the action
     * @param performer the object to which the task will be delegate
     * @param cost the cost of the task
     */
    public BacteriaTask(final Position position, final Bacteria bacteria, final ActionPerformer performer, final Energy cost) {
        super(position, bacteria, performer, cost);
    }
}
