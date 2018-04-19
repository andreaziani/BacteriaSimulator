package model.simulator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import model.Direction;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalAction;
import model.action.DirectionalActionImpl;
import model.bacteria.Bacteria;
import model.bacteria.NotEnoughEnergyException;
import model.food.Food;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import model.state.Position;
import utils.EnvironmentUtil;

/**
 * 
 *
 */
public class ActionManager extends RecursiveAction {

    private static final long serialVersionUID = -4627517274471842922L;
    private static final int THRESHOLD = 2;
    private final List<Position> positions;
    private final BacteriaEnvironment bacteriaEnv;
    private final Map<Position, Food> foodsState;
    private final Position maxPosition;
    private final Optional<Double> maxFoodRadius;
    private final ActionPerformer actionPerformer;
    private final Map<Position, Optional<Position>> foodsPosition = new HashMap<>();

    /**
     * _.
     * 
     * @param positions
     *            _
     * @param bacteriaEnv
     *            _
     * @param foodsState
     *            _
     * @param maxPosition
     *            _
     * @param maxRadius
     *            _
     * @param actionPerf
     *            _
     */
    public ActionManager(final List<Position> positions, final BacteriaEnvironment bacteriaEnv,
            final Map<Position, Food> foodsState, final Position maxPosition, final Optional<Double> maxRadius,
            final ActionPerformer actionPerf) {
        super();
        this.positions = positions;
        this.bacteriaEnv = bacteriaEnv;
        this.foodsState = foodsState;
        this.maxPosition = maxPosition;
        this.maxFoodRadius = maxRadius;
        this.actionPerformer = actionPerf;
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos,
            final Map<Position, Food> foodsState) {
        final int radius = (int) Math.ceil(this.bacteriaEnv.getBacteria(bacteriaPos).getPerceptionRadius());
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvironmentUtil.positionStream(radius, bacteriaPos, maxPosition)
                .map(pos -> Pair.of(pos, EnvironmentUtil.distance(pos, bacteriaPos)))
                .filter(pairPosDist -> pairPosDist.getRight() <= radius)
                .filter(pairPosDist -> foodsState.containsKey(pairPosDist.getLeft())).map(pairPosDist -> {
                    final double angle = EnvironmentUtil.angle(bacteriaPos, pairPosDist.getLeft());
                    final Direction dir = EnvironmentUtil.angleToDir(angle);
                    return Pair.of(dir, pairPosDist.getRight());
                })
                .filter(pairDirDist -> !distsToFood.containsKey(pairDirDist.getLeft())
                        || pairDirDist.getRight() < distsToFood.get(pairDirDist.getLeft()))
                .forEach(pairDirDist -> distsToFood.put(pairDirDist.getLeft(), pairDirDist.getRight()));
        return distsToFood;
    }

    private Optional<Position> collidingFood(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        if (this.maxFoodRadius.isPresent()) {
            final int maxRadius = (int) Math.ceil(this.maxFoodRadius.get());
            final Bacteria bacteria = this.bacteriaEnv.getBacteria(bacteriaPos);
            return EnvironmentUtil.positionStream(maxRadius, bacteriaPos, maxPosition)
                    .filter(pos -> foodsState.containsKey(pos)).map(pos -> Pair.of(pos, foodsState.get(pos)))
                    .filter(pairPosFood -> EnvironmentUtil.isCollision(Pair.of(bacteriaPos, bacteria), pairPosFood))
                    .map(a -> a.getLeft()).findAny();
        } else {
            return Optional.empty();
        }
    }

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final Optional<Position> foodPosition = collidingFood(bacteriaPos, foodsState);
        this.foodsPosition.put(bacteriaPos, foodPosition);
        final Optional<Food> foodInPosition = foodPosition.isPresent() ? Optional.of(foodsState.get(foodPosition.get()))
                : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void costOfLiving(final Bacteria bacteria) {
        try {
            bacteria.spendEnergy(BacteriaManagerImpl.ENERGY_FOR_LIVING);
        } catch (NotEnoughEnergyException e) {
            bacteria.spendEnergy(bacteria.getEnergy());
        }
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
        synchronized (this.actionPerformer) {
            actionPerformer.setStatus(bacteriaPos, bacteria);
            final Action action = bacteria.getAction();
            final ActionType actionType = action.getType();
            
            try {
                bacteria.spendEnergy(bacteria.getActionCost(action));
                switch (actionType) {
                case MOVE:
                    final DirectionalAction moveAction = (DirectionalActionImpl) action;
                    actionPerformer.move(moveAction.getDirection(), moveAction.getDistance());
                    break;
                case EAT:
                    actionPerformer.eat(this.foodsPosition.get(bacteriaPos));
                    break;
                case REPLICATE:
                    final int numberOfBacteria = this.bacteriaEnv.getNumberOfBacteria();
                    actionPerformer.replicate(numberOfBacteria);
                    break;
                default:
                    actionPerformer.doNothing();
                    break;
                }
            } catch (NotEnoughEnergyException e) {
                bacteria.spendEnergy(bacteria.getEnergy());
            }
        }
    }

    @Override
    protected void compute() {
        if (positions.size() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            process(positions);
        }
    }

    private List<ActionManager> createSubtasks() {
        final List<ActionManager> subtask = new ArrayList<>();
        final int halfSize = positions.size() / 2;
        final List<Position> firstHalf = positions.stream().limit(halfSize).collect(Collectors.toList());
        final List<Position> secondHalf = positions.stream().skip(halfSize).collect(Collectors.toList());

        subtask.add(
                new ActionManager(firstHalf, bacteriaEnv, foodsState, maxPosition, maxFoodRadius, actionPerformer));
        subtask.add(new ActionManager(secondHalf, bacteriaEnv, foodsState, maxPosition, maxFoodRadius,
                actionPerformer));
        return subtask;
    }

    private void process(final List<Position> positions) {
        positions.forEach(position -> {
            final Bacteria bacteria = this.bacteriaEnv.getBacteria(position);
            final Perception perception = createPerception(position, foodsState);
            bacteria.setPerception(perception);
            this.costOfLiving(bacteria);
            this.performAction(position, bacteria);
        });
    }
}
