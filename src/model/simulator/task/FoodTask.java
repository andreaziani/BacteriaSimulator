package model.simulator.task;

import model.bacteria.Bacteria;
import model.simulator.FoodEnvironment;
import model.state.Position;

public abstract class FoodTask extends Task {
    protected final FoodEnvironment environment;

    public FoodTask(Position position, Bacteria bacteria, final FoodEnvironment environment) {
        super(position, bacteria);
        this.environment = environment;
    }
}
