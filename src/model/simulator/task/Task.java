package model.simulator.task;

import model.bacteria.Bacteria;
import model.state.Position;

/**
 * Abstract class, with the minimum requirements.
 *
 */
public abstract class Task {
    private final Position position;
    private final Bacteria bacteria;

    /**
     * getter for Position.
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * getter for Bacteria.
     * @return the bacteria
     */
    public Bacteria getBacteria() {
        return bacteria;
    }

    /**
     * Create a task with position and bacteria.
     * @param position the position of the bacteria
     * @param bacteria the bacteria that has to perform the task
     */
    public Task(final Position position, final Bacteria bacteria) {
        this.position = position;
        this.bacteria = bacteria;
    }

    /**
     * Method with the definition of the task to complete.
     */
    public abstract void execute();
}
