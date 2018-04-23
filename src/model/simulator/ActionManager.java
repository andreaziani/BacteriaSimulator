package model.simulator;

import java.util.EnumMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

import model.Direction;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalAction;
import model.bacteria.Bacteria;
import model.bacteria.NotEnoughEnergyException;
import model.food.Food;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import model.state.Position;

/**
 * RecursiveAction to perform action for each Bacteria in parallel.
 */
public final class ActionManager extends RecursiveAction {

    private static final long serialVersionUID = -4627517274471842922L;
    private static final int THRESHOLD = 10;
    private final Stream<Position> positions;
    private final long streamLength;
    private final BacteriaEnvironment bacteriaEnv;
    private final Map<Position, Food> foodsState;
    private final Position maxPosition;
    private final Optional<Double> maxFoodRadius;
    private final ActionPerformer actionPerformer;
    private final Map<Position, Position> foodsPosition = new ConcurrentHashMap<>();
    private final boolean isSafe;

    /**
     * Constructor for ActionManager.
     * 
     * @param positions
     *            A stream representing the Positions of each Bacteria
     * @param length
     *            the length of the current stream
     * @param bacteriaEnv
     *            The environment on which perform the action
     * @param foodsState
     *            The food status used to create the perception
     * @param maxRadius
     *            The the max radius of the food in the simulation
     * @param actionPerf
     *            the Object used to actually perform the action
     * @param isSafe
     *            flag representing whether it's safe to perform this action
     *            considering the map as different sub-maps independent to each
     *            other
     */
    public ActionManager(final Stream<Position> positions, final long length, final BacteriaEnvironment bacteriaEnv,
            final Map<Position, Food> foodsState, final Optional<Double> maxRadius, final ActionPerformer actionPerf,
            final boolean isSafe) {
        super();
        this.positions = positions;
        this.streamLength = length;
        this.bacteriaEnv = bacteriaEnv;
        this.foodsState = foodsState;
        this.maxPosition = this.bacteriaEnv.getMaxPosition();
        this.maxFoodRadius = maxRadius;
        this.actionPerformer = actionPerf;
        this.isSafe = isSafe;
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos) {
        final Bacteria bact = this.bacteriaEnv.getBacteria(bacteriaPos);
        final int radius = (int) Math.ceil(bact.getPerceptionRadius());
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvironmentUtil.positionStream(radius, bacteriaPos, this.maxPosition)
                .filter(pos -> this.foodsState.containsKey(pos)).map(pos -> {
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
                    .filter(pos -> this.foodsState.containsKey(pos)).map(pos -> Pair.of(pos, this.foodsState.get(pos)))
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
                ? Optional.of(this.foodsState.get(foodPosition.get()))
                : Optional.empty();

        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos);

        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void costOfLiving(final Bacteria bacteria) {
        try {
            bacteria.spendEnergy(BacteriaManagerImpl.ENERGY_FOR_LIVING);
        } catch (NotEnoughEnergyException e) {
            bacteria.spendEnergy(bacteria.getEnergy());
        }
    }

    private void performAction(final Position pos, final Bacteria bact) {
        final Action action = bact.getAction();
        final ActionType actionType = action.getType();
        try {
            bact.spendEnergy(bact.getActionCost(action));
            switch (actionType) {
            case MOVE:
                final DirectionalAction moveAction = (DirectionalAction) action;
                actionPerformer.move(pos, bact, moveAction.getDirection(), moveAction.getDistance(), this.isSafe);
                break;
            case EAT:
                final Optional<Position> foodPosition = this.foodsPosition.containsKey(pos)
                        ? Optional.of(this.foodsPosition.get(pos))
                        : Optional.empty();
                actionPerformer.eat(pos, bact, foodPosition);
                break;
            case REPLICATE:
                actionPerformer.replicate(pos, bact, this.isSafe);
                break;
            default:
                actionPerformer.doNothing(pos, bact);
                break;
            }
        } catch (NotEnoughEnergyException e) {
            bact.spendEnergy(bact.getEnergy());
        }
    }

    @Override
    protected void compute() {
        if (this.streamLength <= THRESHOLD || !this.isSafe) {
            solveBaseCase(positions);
        } else {
            try {
                ForkJoinTask.invokeAll(divideSubtask());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ActionManager[] divideSubtask() {
        final long halfSize = this.streamLength / 2;
        final List<Position> stream = positions.collect(Collectors.toList());

        final ActionManager firstHalf = new ActionManager(stream.stream().limit(halfSize), halfSize, bacteriaEnv,
                foodsState, maxFoodRadius, actionPerformer, isSafe);

        final ActionManager secondHalf = new ActionManager(stream.stream().skip(halfSize), this.streamLength - halfSize,
                bacteriaEnv, foodsState, maxFoodRadius, actionPerformer, isSafe);

        return new ActionManager[] { firstHalf, secondHalf };
    }

    private void solveBaseCase(final Stream<Position> positions) {
        positions.map(pos -> Pair.of(pos, this.bacteriaEnv.getBacteria(pos)))
                .peek(posBact -> posBact.getRight().setPerception(this.createPerception(posBact.getLeft())))
                .peek(posBact -> this.costOfLiving(posBact.getRight()))
                .forEach(posBact -> this.performAction(posBact.getLeft(), posBact.getRight()));
    }
}
