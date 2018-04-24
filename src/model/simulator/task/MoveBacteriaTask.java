package model.simulator.task;

import java.util.Optional;

import model.Direction;
import model.bacteria.Bacteria;
import model.simulator.BacteriaEnvironment;
import model.simulator.EnvironmentUtil;
import model.state.Position;
import utils.Logger;

public class MoveBacteriaTask extends BacteriaTask {
    private final Direction direction;
    private final double distance;

    public MoveBacteriaTask(Position position, Bacteria bacteria, BacteriaEnvironment environment, final Position maxPosition, final Direction direction, final double distance) {
        super(position, bacteria, environment, maxPosition);
        this.direction = direction;
        this.distance = distance;
    }

    @Override
    public void execute() {
        Logger.getInstance().info("MOVE " + this.environment.getQuadrant(position),
                "THREAD" + Thread.currentThread().getId() + " IN");
        final double maximumDistance = bacteria.getSpeed() * EnvironmentUtil.UNIT_OF_TIME;
        final double distance = Math.min(this.distance, maximumDistance);
        this.environment.clearPosition(position, bacteria);

        final Optional<Position> newPosition = EnvironmentUtil
                .circularPositionStream((int) Math.ceil(distance), position, this.maxPosition)
                .filter(posGen -> EnvironmentUtil
                        .angleToDir(EnvironmentUtil.angle(position, posGen)) == direction)
                .filter(posGen -> EnvironmentUtil.causeCollision(posGen,
                        (int) Math.ceil(bacteria.getRadius()), maxPosition,
                        pos -> this.environment.isPositionOccupied(pos)))
                .max((p1, p2) -> Double.compare(EnvironmentUtil.distance(position, p1),
                        EnvironmentUtil.distance(position, p2)));

        if (newPosition.isPresent()) {
            this.environment.changeBacteriaPosition(position, newPosition.get());
            this.environment.markPosition(newPosition.get(), bacteria);
        } else {
            this.environment.markPosition(position, bacteria);
        }
        Logger.getInstance().info("MOVE " + this.environment.getQuadrant(position),
                "THREAD" + Thread.currentThread().getId() + " OUT");
    }

}
