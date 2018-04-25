package model.simulator.bacteria.task;

/**
 * Interface representing a task.
 *
 */
public interface Task {
    /**
     * Only method a task should implement.
     * @param isSafe flag determine if the current task can be executed concurrently
     */
    void execute(boolean isSafe);
}
