package model.simulator.task;

import model.bacteria.Bacteria;
import model.simulator.BacteriaEnvironment;
import model.state.Position;

/**
 * Task that interact with the bacteria environment.
 *
 */
public abstract class BacteriaTask extends Task {
    private final BacteriaEnvironment  environment;
    private final Position maxPosition;

    /**
     * Return the environment in which perform the action.
     * @return the {@link BacteriaEnvironment}
     */
    public BacteriaEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Return the maxPosition of the environment.
     * @return the maxPosition
     */
    public Position getMaxPosition() {
        return maxPosition;
    }

    /**
     * Construct a BacteriaTask.
     * @param position the position of the bacteria
     * @param bacteria the bacteria that has to perform the action
     * @param environment the environment in which perform the action
     * @param maxPosition the max position in the environment
     */
    public BacteriaTask(final Position position, final Bacteria bacteria, final BacteriaEnvironment environment, final Position maxPosition) {
        super(position, bacteria);
        this.environment = environment;
        this.maxPosition = maxPosition;
    }
}
