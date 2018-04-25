package model.simulator.bacteria;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import model.Direction;
import model.Energy;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalAction;
import model.bacteria.Bacteria;
import model.food.Food;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import model.simulator.EnvironmentUtil;
import model.simulator.bacteria.task.EatFoodTask;
import model.simulator.bacteria.task.MoveBacteriaTask;
import model.simulator.bacteria.task.ReplicateBacteriaTask;
import model.simulator.bacteria.task.SolelyTask;
import model.simulator.bacteria.task.Task;
import model.simulator.food.FoodEnvironment;
import model.state.Position;

/**
 * Class resposibile for creating task.
 *
 */
public final class TaskCreator extends RecursiveTask<List<Pair<Position, Task>>> {

    private static final long serialVersionUID = -4627517274471842922L;
    private static final int THRESHOLD = 5;
    private final Stream<Position> positions;
    private final long streamLength;
    private final BacteriaEnvironment bacteriaEnv;
    private final FoodEnvironment foodEnv;
    private final ActionPerformer performer;
    private final Position maxPosition;
    private final Optional<Double> maxFoodRadius;
    private final Map<Position, Position> foodsPosition = new ConcurrentHashMap<>();

    /**
     * Constructor for ActionManager.
     * 
     * @param positions
     *            A stream representing the Positions of each Bacteria
     * @param length
     *            the length of the current stream
     * @param bacteriaEnv
     *            The environment on which perform the action
     * @param foodEnv
     *            The environment representing the foods
     * @param maxPosition
     *            The max position in the simulation
     * @param maxRadius
     *            The max radius of the food in the simulation
     * @param performer
     *            the Object used to actually perform the action
     */
    public TaskCreator(final Stream<Position> positions, final long length, final BacteriaEnvironment bacteriaEnv,
            final FoodEnvironment foodEnv, final ActionPerformer performer, final Position maxPosition,
            final Optional<Double> maxRadius) {
        super();
        this.positions = positions;
        this.streamLength = length;
        this.bacteriaEnv = bacteriaEnv;
        this.foodEnv = foodEnv;
        this.performer = performer;
        this.maxPosition = maxPosition;
        this.maxFoodRadius = maxRadius;
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos) {
        final Bacteria bact = this.bacteriaEnv.getBacteria(bacteriaPos);
        final int radius = (int) Math.ceil(bact.getPerceptionRadius());
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvironmentUtil.positionStream(radius, bacteriaPos, this.maxPosition)
                .filter(pos -> this.foodEnv.getFoodsState().containsKey(pos)).map(pos -> {
                    final double angle = EnvironmentUtil.angle(bacteriaPos, pos);
                    final Direction dir = EnvironmentUtil.angleToDir(angle);
                    return Pair.of(dir, EnvironmentUtil.distance(pos, bacteriaPos));
                }).filter(dirDist -> dirDist.getRight() < distsToFood.getOrDefault(dirDist.getLeft(), Double.MAX_VALUE))
                .forEach(dirDist -> distsToFood.compute(dirDist.getLeft(), (k, v) -> dirDist.getRight()));

        return distsToFood;
    }

    private Optional<Position> collidingFood(final Position bacteriaPos) {
        if (this.maxFoodRadius.isPresent()) {
            final Bacteria bacteria = this.bacteriaEnv.getBacteria(bacteriaPos);
            final int distance = (int) Math.floor(this.maxFoodRadius.get() + bacteria.getRadius());

            return EnvironmentUtil.positionStream(distance, bacteriaPos, this.maxPosition)
                    .filter(pos -> this.foodEnv.getFoodsState().containsKey(pos))
                    .map(pos -> Pair.of(pos, this.foodEnv.getFoodsState().get(pos)))
                    .filter(pairPosFood -> EnvironmentUtil.isCollision(Pair.of(bacteriaPos, bacteria), pairPosFood))
                    .findAny().map(a -> a.getLeft());
        } else {
            return Optional.empty();
        }
    }

    private Perception createPerception(final Position bacteriaPos) {
        final Optional<Position> foodPosition = collidingFood(bacteriaPos);

        if (foodPosition.isPresent()) {
            this.foodsPosition.put(bacteriaPos, foodPosition.get());
        }

        final Optional<Food> foodInPosition = foodPosition.isPresent()
                ? Optional.of(this.foodEnv.getFoodsState().get(foodPosition.get()))
                : Optional.empty();

        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos);

        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    @Override
    protected List<Pair<Position, Task>> compute() {
        if (this.streamLength <= THRESHOLD) {
            return solveBaseCase(positions);
        } else {
            final List<Pair<Position, Task>> allTask = new ArrayList<>();
            final TaskCreator[] tasks = divideSubtask();
            for (final TaskCreator t : tasks) {
                allTask.addAll(t.join());
            }
            return allTask;
        }
    }

    private TaskCreator[] divideSubtask() {
        final long halfSize = this.streamLength / 2;
        final List<Position> stream = positions.collect(Collectors.toList());

        final TaskCreator firstHalf = new TaskCreator(stream.stream().limit(halfSize), halfSize, this.bacteriaEnv,
                this.foodEnv, this.performer, this.maxPosition, this.maxFoodRadius);

        final TaskCreator secondHalf = new TaskCreator(stream.stream().skip(halfSize), this.streamLength - halfSize,
                this.bacteriaEnv, this.foodEnv, this.performer, this.maxPosition, this.maxFoodRadius);

        firstHalf.fork();
        secondHalf.fork();

        return new TaskCreator[] { firstHalf, secondHalf };
    }

    private Pair<Position, Task> createTask(final Position position, final Bacteria bacteria) {
        final Action action = bacteria.getAction();
        final ActionType actionType = action.getType();
        final Energy cost = bacteria.getActionCost(action);
        switch (actionType) {
        case MOVE:
            final DirectionalAction moveAction = (DirectionalAction) action;
            return Pair.of(position, new MoveBacteriaTask(position, bacteria, performer, cost,
                    moveAction.getDirection(), moveAction.getDistance()));
        case EAT:
            final Optional<Position> foodPosition = this.foodsPosition.containsKey(position)
                    ? Optional.of(this.foodsPosition.get(position))
                    : Optional.empty();
            return Pair.of(position, new EatFoodTask(position, bacteria, performer, cost, foodPosition));
        case REPLICATE:
            return Pair.of(position,
                    (Task) new ReplicateBacteriaTask(position, bacteria, performer, cost));
        default:
            return Pair.of(position, new SolelyTask(position, bacteria, performer, cost));
        }
    }

    private List<Pair<Position, Task>> solveBaseCase(final Stream<Position> positions) {
        return positions.map(pos -> Pair.of(pos, this.bacteriaEnv.getBacteria(pos)))
                .peek(posBact -> posBact.getRight().setPerception(this.createPerception(posBact.getLeft())))
                .map(posBact -> this.createTask(posBact.getLeft(), posBact.getRight())).collect(Collectors.toList());
    }
}
