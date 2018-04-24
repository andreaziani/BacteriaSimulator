package model.simulator.task;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.ActionPerformer;
import model.state.Position;

/**
 * Abstract class, with the minimum requirements.
 *
 */
public abstract class Task {
    private final Position position;
    private final Bacteria bacteria;
    private final ActionPerformer performer;
    private final Energy taskCost;

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

    public ActionPerformer getPerformer() {
        return this.performer;
    }

    public Energy getCost() {
        return this.taskCost;
    }

    /**
     * Create a task with position and bacteria.
     * @param position the position of the bacteria
     * @param bacteria the bacteria that has to perform the task
     */
    public Task(final Position position, final Bacteria bacteria, final ActionPerformer performer, final Energy cost) {
        this.position = position;
        this.bacteria = bacteria;
        this.performer = performer;
        this.taskCost = cost;
    }

    /**
     * Method with the definition of the task to complete.
     */
    public abstract void execute(boolean isSafe);
}
