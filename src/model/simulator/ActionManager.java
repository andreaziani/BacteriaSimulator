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
 * RecursiveAction to perform action for each Bacteria in parallel.
 */
public class ActionManager extends RecursiveAction {

    private static final long serialVersionUID = -4627517274471842922L;
    private static final int THRESHOLD = 4;
    private final List<Position> positions;
    private final BacteriaEnvironment bacteriaEnv;
    private final Map<Position, Food> foodsState;
    private final Position maxPosition;
    private final Optional<Double> maxFoodRadius;
    private final ActionPerformer actionPerformer;
    private final Map<Position, Optional<Position>> foodsPosition = new HashMap<>();

    /**
     * Constructor for ActionManager.
     * 
     * @param positions
     *            The Positions of each Bacteria
     * @param bacteriaEnv
     *            The environment on which perform the action
     * @param foodsState
     *            The food status used to create the perception
     * @param maxRadius
     *            The the max radius of the food in the simulation
     * @param actionPerf
     *            the Object used to actually perform the action
     */
    public ActionManager(final List<Position> positions, final BacteriaEnvironment bacteriaEnv,
            final Map<Position, Food> foodsState, final Optional<Double> maxRadius,
            final ActionPerformer actionPerf) {
        super();
        this.positions = positions;
        this.bacteriaEnv = bacteriaEnv;
        this.foodsState = foodsState;
        this.maxPosition = this.bacteriaEnv.getMaxPosition();
        this.maxFoodRadius = maxRadius;
        this.actionPerformer = actionPerf;
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos) {
        final int radius = (int) Math.ceil(this.bacteriaEnv.getBacteria(bacteriaPos).getPerceptionRadius());
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvironmentUtil.positionStream(radius, bacteriaPos, this.maxPosition)
                .filter(pos -> this.foodsState.containsKey(pos)).map(pos -> {
                    final double angle = EnvironmentUtil.angle(bacteriaPos, pos);
                    final Direction dir = EnvironmentUtil.angleToDir(angle);
                    return Pair.of(dir, EnvironmentUtil.distance(pos, bacteriaPos));
                })
                .filter(pairDirDist -> !distsToFood.containsKey(pairDirDist.getLeft())
                        || pairDirDist.getRight() < distsToFood.get(pairDirDist.getLeft()))
                .forEach(pairDirDist -> distsToFood.put(pairDirDist.getLeft(), pairDirDist.getRight()));
        return distsToFood;
    }

    private Optional<Position> collidingFood(final Position bacteriaPos) {
        if (this.maxFoodRadius.isPresent()) {
            final int maxRadius = (int) Math.ceil(this.maxFoodRadius.get());
            final Bacteria bacteria = this.bacteriaEnv.getBacteria(bacteriaPos);
            return EnvironmentUtil.positionStream(maxRadius, bacteriaPos, this.maxPosition)
                    .filter(pos -> this.foodsState.containsKey(pos)).map(pos -> Pair.of(pos, this.foodsState.get(pos)))
                    .filter(pairPosFood -> EnvironmentUtil.isCollision(Pair.of(bacteriaPos, bacteria), pairPosFood))
                    .findAny().map(a -> a.getLeft());
        } else {
            return Optional.empty();
        }
    }

    private Perception createPerception(final Position bacteriaPos) {
        final Optional<Position> foodPosition = collidingFood(bacteriaPos);
        this.foodsPosition.put(bacteriaPos, foodPosition);
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
                    // TODO mutex for action that modify bacteriaEnv
                    final DirectionalAction moveAction = (DirectionalActionImpl) action;
                    actionPerformer.move(pos, bact, moveAction.getDirection(), moveAction.getDistance());
                    break;
                case EAT:
                    // TODO mutex for action that modify foodEnv
                    actionPerformer.eat(pos, bact, this.foodsPosition.get(pos));
                    break;
                case REPLICATE:
                    // TODO mutex for action that modify bacteriaEnv
                    final int numberOfBacteria = this.bacteriaEnv.getNumberOfBacteria();
                    actionPerformer.replicate(pos, bact, numberOfBacteria);
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
        if (positions.size() <= THRESHOLD) {
            solveBaseCase(positions);
        } else {
            ForkJoinTask.invokeAll(divideSubtask());
        }
    }

    private List<ActionManager> divideSubtask() {
        final List<ActionManager> subtask = new ArrayList<>();
        final int halfSize = positions.size() / 2;
        final List<Position> fHalf = positions.stream().limit(halfSize).collect(Collectors.toList());
        final List<Position> sHalf = positions.stream().skip(halfSize).collect(Collectors.toList());

        subtask.add(new ActionManager(fHalf, bacteriaEnv, foodsState, maxFoodRadius, actionPerformer));
        subtask.add(new ActionManager(sHalf, bacteriaEnv, foodsState, maxFoodRadius, actionPerformer));

        return subtask;
    }

    private void solveBaseCase(final List<Position> positions) {
        positions.forEach(position -> {
            final Bacteria bacteria = this.bacteriaEnv.getBacteria(position);
            bacteria.setPerception(createPerception(position));
            costOfLiving(bacteria);
            performAction(position, bacteria);
        });
    }
}
