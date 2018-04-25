package model.simulator.bacteria.task;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.bacteria.ActionPerformer;
import model.state.Position;

/**
 * Task that represent a replication.
 *
 */
public final class ReplicateBacteriaTask extends BacteriaTask {

    /**
     * @param position the position of the bacteria
     * @param bacteria the bacteria on which perform the action
     * @param performer the performer used to execute the action
     * @param cost the cost of the action
     */
    public ReplicateBacteriaTask(final Position position, final Bacteria bacteria, final ActionPerformer performer, final Energy cost) {
        super(position, bacteria, performer, cost);
    }

    @Override
    public void execute(final boolean isSafe) {
        this.getPerformer().replicate(getPosition(), getBacteria(), isSafe, getCost());
    }
}
