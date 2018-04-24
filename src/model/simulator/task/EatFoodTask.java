package model.simulator.task;

import java.util.Optional;

import model.bacteria.Bacteria;
import model.food.Food;
import model.simulator.FoodEnvironment;
import model.state.Position;
import utils.Logger;

/**
 * Task representing a Eat action.
 *
 */
public class EatFoodTask extends FoodTask {
    private final Optional<Position> foodPosition;

    /**
     * Construct a eat task.
     * @param position
     * @param bacteria
     * @param environment
     * @param foodPosition
     */
    public EatFoodTask(Position position, Bacteria bacteria, FoodEnvironment environment,
            final Optional<Position> foodPosition) {
        super(position, bacteria, environment);
        this.foodPosition = foodPosition;
    }

    @Override
    public void execute() {
        Logger.getInstance().info("EAT", "THREAD" + Thread.currentThread().getId() + " IN");
        Optional<Food> foodInPosition = Optional.empty();
        if (foodPosition.isPresent() && this.environment.getFoodsState().containsKey(foodPosition.get())) {
            foodInPosition = Optional.of(this.environment.getFoodsState().get(foodPosition.get()));
        }

        if (foodInPosition.isPresent()) {
            bacteria.addFood(foodInPosition.get());
            this.environment.removeFood(foodInPosition.get(), foodPosition.get());
        }
        Logger.getInstance().info("EAT", "THREAD" + Thread.currentThread().getId() + " OUT");
    }
}
