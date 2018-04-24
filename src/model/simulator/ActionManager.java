package model.simulator;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

import model.simulator.task.Task;
import model.state.Position;

/**
 * RecursiveAction to perform action for each Bacteria in parallel.
 */
public final class ActionManager extends RecursiveAction {

    private static final long serialVersionUID = -4627517274471842922L;
    private static final int THRESHOLD = 5;
    private final Stream<Pair<Position, Task>> positions;
    private final long streamLength;
    private final boolean isSafe;

    /**
     * Constructor for ActionManager.
     * 
     * @param positions
     *            A stream representing the Positions of each Bacteria
     * @param length
     *            the length of the current stream
     * @param bacteriaEnv
     *            The environment on which perform the action
     * @param foodsState
     *            The food status used to create the perception
     * @param maxPosition
     *            The max position in the simulation
     * @param maxRadius
     *            The max radius of the food in the simulation
     * @param performer
     *            the Object used to actually perform the action
     * @param isSafe
     *            flag representing whether it's safe to perform this action
     *            considering the map as different sub-maps independent to each
     *            other
     */
    public ActionManager(final Stream<Pair<Position, Task>> positions, final long length, final boolean isSafe) {
        super();
        this.positions = positions;
        this.streamLength = length;
        this.isSafe = isSafe;
    }

    @Override
    protected void compute() {
        if (this.streamLength <= THRESHOLD || !this.isSafe) {
            solveBaseCase(positions);
        } else {
            try {
                ForkJoinTask.invokeAll(divideSubtask());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ActionManager[] divideSubtask() {
        final long halfSize = this.streamLength / 2;
        final List<Pair<Position, Task>> stream = positions.collect(Collectors.toList());

        final ActionManager firstHalf = new ActionManager(stream.stream().limit(halfSize), halfSize, this.isSafe);

        final ActionManager secondHalf = new ActionManager(stream.stream().skip(halfSize), this.streamLength - halfSize,
                this.isSafe);

        return new ActionManager[] { firstHalf, secondHalf };
    }

    private void solveBaseCase(final Stream<Pair<Position, Task>> positions) {
        positions.forEach(x -> {
            x.getRight().execute(isSafe);
        });
    }
}
