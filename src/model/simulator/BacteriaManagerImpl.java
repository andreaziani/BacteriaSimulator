package model.simulator;

import java.util.EnumMap;
import java.util.HashSet;
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
import model.NotEnounghEnergyException;
import model.PositionAlreadyOccupiedException;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalAction;
import model.action.DirectionalActionImpl;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.species.SpeciesManager;
import model.food.ExistingFoodManager;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.geneticcode.Gene;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCode;
import model.geneticcode.GeneticCodeImpl;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import model.state.Position;
import model.state.PositionImpl;
import utils.EnvironmentUtil;
import utils.Logger;

/**
 * Implementation of BacteriaManager.
 *
 */
public class BacteriaManagerImpl implements BacteriaManager {
    //private static final int MAX_BACTERIA = 1000;
    private static final double COST_OF_LIVING = 1.5;
    private static final int BACTERIA_PER_SPECIES = 50;
    private final Position simulationMaxPosition;
    private final ExistingFoodManager manager;
    private final FoodEnvironment foodEnv;
    private final Energy energyForLiving = new EnergyImpl(COST_OF_LIVING);
    private final FoodFactory factory = new FoodFactoryImpl();
    private final SpeciesManager speciesManager;
    private final BacteriaEnvironment bacteriaEnv;
    private final ActionPerformer actionPerformer;
    private final Random rand = new Random();
    private Optional<Double> maxFoodRadius;
    private Optional<Position> foodPosition;
    private int bacteriaCounter;

    /**
     * Constructor.
     * 
     * @param foodEnv
     *            used to update food environment according to bacteria actions
     * @param manager
     *            food manager used to fast retrieve all kind of food in simulation
     * @param maxPosition
     *            contains information about the maximum position in the simulation
     * @param speciesManager
     *            species manager containing information about existing species
     */
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final ExistingFoodManager manager, final Position maxPosition, final SpeciesManager speciesManager) {
        this.bacteriaCounter = 0;
        this.foodEnv = foodEnv;
        this.manager = manager;
        this.simulationMaxPosition = maxPosition;
        this.speciesManager = speciesManager;
        this.bacteriaEnv = new BacteriaEnvironmentImpl(maxPosition);
        this.actionPerformer = new ActionPerformerImpl(bacteriaEnv, foodEnv, maxPosition);
    }

    @Override
    public void populate(final Optional<Map<Position, Bacteria>> bacteriaState) {
        Logger.getInstance().info("Bacteria Manager", "Populating simulation");
        if (bacteriaState.isPresent()) {
            bacteriaState.get().entrySet().stream().forEach(entry -> this.bacteriaEnv.insertBacteria(entry.getKey(), entry.getValue()));
        } else {
            this.speciesManager.getSpecies().stream().forEach(specie -> {
                final Gene gene = new GeneImpl();
                IntStream.range(0, BACTERIA_PER_SPECIES)
                        .mapToObj(x -> new PositionImpl(rand.nextInt((int) this.simulationMaxPosition.getX()),
                                rand.nextInt((int) this.simulationMaxPosition.getY())))
                        .forEach(position -> {
                            final GeneticCode genCode = new GeneticCodeImpl(gene, 5.0, 25.0);
                            final Bacteria bacteria = new BacteriaImpl(bacteriaCounter, specie, genCode,
                                    SimulatorEnvironment.INITIAL_ENERGY);
                            bacteriaCounter++;
                            this.bacteriaEnv.insertBacteria(position, bacteria);
                        });
            });
        }
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos,
            final Map<Position, Food> foodsState) {
        final int radius = (int) Math.ceil(this.bacteriaEnv.getBacteria(bacteriaPos).getPerceptionRadius());
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvironmentUtil.positionStream(radius, bacteriaPos, simulationMaxPosition)
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
            return EnvironmentUtil.positionStream(maxRadius, bacteriaPos, simulationMaxPosition)
                    .filter(pos -> foodsState.containsKey(pos)).map(pos -> Pair.of(pos, foodsState.get(pos)))
                    .filter(pairPosFood -> EnvironmentUtil.isCollision(Pair.of(bacteriaPos, bacteria), pairPosFood))
                    .map(a -> a.getLeft()).findAny();
        } else {
            return Optional.empty();
        }
    }

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        this.foodPosition = collidingFood(bacteriaPos, foodsState);
        final Optional<Food> foodInPosition = this.foodPosition.isPresent()
                ? Optional.of(foodsState.get(this.foodPosition.get()))
                : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
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
                actionPerformer.eat(this.foodPosition);
                break;
            case REPLICATE:
                if (actionPerformer.replicate(bacteriaCounter)) {
                    bacteriaCounter++;
                }
                break;
            default:
                actionPerformer.doNothing();
                break;
            }
        } catch (NotEnounghEnergyException e) {
            bacteria.spendEnergy(bacteria.getEnergy());
        }
    }

    private void costOfLiving(final Bacteria bacteria) {
        try {
            bacteria.spendEnergy(this.energyForLiving);
        } catch (NotEnounghEnergyException e) {
            bacteria.spendEnergy(bacteria.getEnergy());
        }
    }

    private void updateAliveBacteria() {
        this.bacteriaEnv.updateOccupiedPositions();
        this.maxFoodRadius = this.manager.getExistingFoodsSet().stream().map(food -> food.getRadius())
                .max((r1, r2) -> Double.compare(r1, r2));

        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        final Set<Position> positions = new HashSet<Position>(this.bacteriaEnv.activePosition());

        positions.stream().map(pos -> Pair.of(pos, this.bacteriaEnv.getBacteria(pos)))
                .peek(posBact -> posBact.getRight().setPerception(this.createPerception(posBact.getLeft(), foodsState)))
                .peek(posBact -> this.costOfLiving(posBact.getRight()))
                .forEach(posBact -> this.performAction(posBact.getLeft(), posBact.getRight()));
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteriaEnv.entrySet().stream()
                .filter(entry -> entry.getValue().isDead())
                .peek(entry -> {
                    try {
                        this.foodEnv.addFood(entry.getValue().getInternalFood(this.factory), entry.getKey());
                    } catch (PositionAlreadyOccupiedException e) {
                        // Food collided with other food nearby, just don't add
                        Logger.getInstance().info("BacteriaManager", "Bacteria died on Food");
                    }
                })
                .map(entry -> entry.getKey()).collect(Collectors.toSet());
        this.bacteriaEnv.removeFromPositions(toBeRemoved);
    }

    /**
     * Update Bacteria every turn.
     */
    @Override
    public void updateBacteria() {
        this.updateDeadBacteria();
        this.updateAliveBacteria();
    }

    /**
     * Implementation of getBacteriaState.
     */
    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return this.bacteriaEnv.getBacteriaState();
    }
}
