package model.simulator;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import model.Direction;
import model.Energy;
import model.EnergyImpl;
import model.Position;
import model.PositionImpl;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalAction;
import model.action.DirectionalActionImpl;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.Species;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCode;
import model.geneticcode.GeneticCodeImpl;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import utils.EnvironmentUtil;
import utils.Log;

/**
 * Implementation of BacteriaManager.
 *
 */
public class BacteriaManagerImpl implements BacteriaManager {
    private static final Energy INITIAL_ENERGY = new EnergyImpl(10.0);
    private static final double COST_OF_LIVING = 2.0;
    private static final int BACTERIA_PER_SPECIES = 15;
    private final Position simulationMaxPosition;
    private final FoodEnvironment foodEnv;
    private final Energy energyForLiving = new EnergyImpl(COST_OF_LIVING);
    private final FoodFactory factory = new FoodFactoryImpl();
    private final BacteriaEnvironment bacteriaEnv;
    private final ActionPerformer actionPerf;
    private final Random rand = new Random();
    private int bacteriaCounter;
    /**
     * Constructor.
     * 
     * @param foodEnv
     *            used to update food environment according to bacteria actions
     * @param maxPosition
     *            contains information about the maximum position in the simulation
     * @param species
     *            existing species used to create the Bacteria
     */
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final Position maxPosition, final Set<Species> species) {
        this.bacteriaCounter = 0;
        this.simulationMaxPosition = maxPosition;
        this.foodEnv = foodEnv;
        this.bacteriaEnv = new BacteriaEnvironmentImpl();
        this.actionPerf = new ActionPerformerImpl(bacteriaEnv, foodEnv);

        Log.getLog().info("Start populating..");
        species.stream().forEach(specie -> IntStream.range(0, BACTERIA_PER_SPECIES)
                .mapToObj(x -> new PositionImpl(rand.nextInt((int) this.simulationMaxPosition.getX()), rand.nextInt((int) this.simulationMaxPosition.getY())))
                .forEach(position -> {
                    final GeneticCode genCode = new GeneticCodeImpl(new GeneImpl(), 2.0, 3.5);
                    final Bacteria bacteria = new BacteriaImpl(bacteriaCounter, specie, genCode, INITIAL_ENERGY);
                    bacteriaCounter++;
                    this.bacteriaEnv.insertBacteria(position, bacteria);
                }));
        this.bacteriaEnv.getBacteriaState().entrySet().stream().forEach(entry -> Log.getLog().info(entry.getValue().toString()));
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final double radius = this.bacteriaEnv.getBacteria(bacteriaPos).getPerceptionRadius();
        final int start = (int) -Math.ceil(radius);
        final int end = (int) Math.ceil(radius);
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvironmentUtil.positionStream(start, end, bacteriaPos)
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

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final Optional<Food> foodInPosition = foodsState.containsKey(bacteriaPos)
                ? Optional.of(foodsState.get(bacteriaPos))
                : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
        actionPerf.setStatus(bacteriaPos, bacteria);
        final Action action = bacteria.getAction();
        final ActionType actionType = action.getType();

        if (bacteria.getEnergy().compareTo(bacteria.getActionCost(action)) > 0) {
            switch (actionType) {
            case MOVE:
                final DirectionalAction moveAction = (DirectionalActionImpl) action;
                actionPerf.move(moveAction.getDirection());
                break;
            case EAT:
                actionPerf.eat();
                break;
            case REPLICATE:
                if (actionPerf.replicate(bacteriaCounter)) {
                    bacteriaCounter++;
                }
                break;
            default:
                actionPerf.doNothing();
                break;
            }
            bacteria.spendEnergy(bacteria.getActionCost(action));
        } else {
            bacteria.spendEnergy(bacteria.getEnergy());
        }
    }

    private void costOfLiving(final Bacteria bacteria) {
        bacteria.spendEnergy(this.energyForLiving);
    }

    private void updateLivingBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        this.bacteriaEnv.entrySet().stream()
                .peek(e -> e.getValue().setPerception(this.createPerception(e.getKey(), foodsState)))
                .peek(e -> this.performAction(e.getKey(), e.getValue())).forEach(e -> this.costOfLiving(e.getValue()));
    }

    private void updateDeadBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        final Set<Position> toBeRemoved = this.bacteriaEnv.entrySet().stream()
                .filter(e -> e.getValue().isDead())
                .peek(e -> {
                    if (!foodsState.containsKey(e.getKey())) {
                        this.foodEnv.addFood(e.getValue().getInternalFood(this.factory), e.getKey());
                    }
                })
                .map(e -> e.getKey()).collect(Collectors.toSet());
        this.bacteriaEnv.removeFromPositions(toBeRemoved);
    }

    /**
     * Update Bacteria every turn.
     */
    @Override
    public void updateBacteria() {
        this.updateDeadBacteria();
        this.updateLivingBacteria();
    }

    /**
     * Implementation of getBacteriaState.
     */
    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return this.bacteriaEnv.getBacteriaState();
    }
}
