package model.simulator.bacteria.task;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.bacteria.ActionPerformer;
import model.state.Position;

/**
 * Abstract class, with the minimum requirements.
 *
 */
public abstract class TaskBase implements Task {
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

    /**
     * Get the performer used to execute the action.
     * @return the performer to execute the action
     */
    public ActionPerformer getPerformer() {
        return this.performer;
    }

    /**
     * Return the cost of the current task.
     * @return the cost in energy
     */
    public Energy getCost() {
        return this.taskCost;
    }

    /**
     * Create a task with position and bacteria.
     * @param position the position of the bacteria
     * @param bacteria the bacteria that has to perform the task
     * @param performer the performer used to execute the action
     * @param cost the cost of the action
     */
    public TaskBase(final Position position, final Bacteria bacteria, final ActionPerformer performer, final Energy cost) {
        this.position = position;
        this.bacteria = bacteria;
        this.performer = performer;
        this.taskCost = cost;
    }

    /**
     * Method with the definition of the task to complete.
     * @param isSafe determine if the current task can be executed concurrently
     */
    public abstract void execute(boolean isSafe);
}
