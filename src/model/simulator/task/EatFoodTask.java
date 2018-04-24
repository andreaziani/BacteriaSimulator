package model.simulator.task;

import java.util.Optional;

import model.Energy;
import model.bacteria.Bacteria;
import model.simulator.ActionPerformer;
import model.state.Position;

/**
 * Task representing a Eat action.
 *
 */
public final class EatFoodTask extends FoodTask {
    private final Optional<Position> foodPosition;

    /**
     * Construct a EatFoodTask.
     * 
     * @param position
     *            the position of the bacteria
     * @param bacteria
     *            the bacteria that has to perform the action
     * @param performer
     *            the object to which the task will be delegate
     * @param cost
     *            the cost of the task
     * @param foodPosition
     *            the food the bacteria should eat
     */
    public EatFoodTask(final Position position, final Bacteria bacteria, final ActionPerformer performer,
            final Energy cost, final Optional<Position> foodPosition) {
        super(position, bacteria, performer, cost);
        this.foodPosition = foodPosition;
    }

    @Override
    public void execute(final boolean isSafe) {
        this.getPerformer().eat(getPosition(), getBacteria(), foodPosition, isSafe, this.getCost());
    }
}
