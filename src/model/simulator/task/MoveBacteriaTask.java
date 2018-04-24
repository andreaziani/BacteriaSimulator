package model.simulator.task;

import model.Direction;
import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.ActionPerformer;
import model.state.Position;

public final class MoveBacteriaTask extends BacteriaTask {
    private final Direction direction;
    private final double distance;

    /**
     * Construct a MoveBacteriaTask.
     * 
     * @param position
     *            the position of the bacteria
     * @param bacteria
     *            the bacteria that has to perform the action
     * @param performer
     *            the object to which the task will be delegate
     * @param cost
     *            the cost of the task
     * @param environment
     *            the environment in which perform the action
     * @param foodPosition
     *            the food the bacteria should eat
     */
    public MoveBacteriaTask(final Position position, final Bacteria bacteria, final ActionPerformer performer,
            final Energy cost, final Direction direction, final double distance) {
        super(position, bacteria, performer, cost);
        this.direction = direction;
        this.distance = distance;
    }

    @Override
    public void execute(final boolean isSafe) {
        this.getPerformer().move(getPosition(), getBacteria(), direction, distance, isSafe, this.getCost());
    }

}
