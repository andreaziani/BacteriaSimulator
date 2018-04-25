package model.simulator.bacteria.task;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.bacteria.ActionPerformer;
import model.state.Position;

public final class ReplicateBacteriaTask extends BacteriaTask {

    public ReplicateBacteriaTask(Position position, Bacteria bacteria, final ActionPerformer performer, final Energy cost) {
        super(position, bacteria, performer, cost);
    }

    @Override
    public void execute(final boolean isSafe) {
        this.getPerformer().replicate(getPosition(), getBacteria(), isSafe, getCost());
    }
}
