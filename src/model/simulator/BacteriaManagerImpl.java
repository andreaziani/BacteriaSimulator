package model.simulator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.Energy;
import model.EnergyImpl;
import model.PositionAlreadyOccupiedException;
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
import model.state.Position;
import model.state.PositionImpl;
import utils.Logger;

/**
 * Implementation of BacteriaManager.
 *
 */
public class BacteriaManagerImpl implements BacteriaManager {
    private static final double COST_OF_LIVING = 1.5;
    private static final int BACTERIA_PER_SPECIES = 100;
    private static final double BACTERIA_RADIUS = 5.0;
    private static final double PERCEPTION_RADIUS = 25.0;
    private final Position simulationMaxPosition;
    private final ExistingFoodManager manager;
    private final FoodEnvironment foodEnv;
    private final FoodFactory factory = new FoodFactoryImpl();
    private final SpeciesManager speciesManager;
    private final BacteriaEnvironment bacteriaEnv;
    private final ActionPerformer actionPerformer;
    private final Random rand = new Random();
    private int bacteriaCounter;

    private final ForkJoinPool commonPool = new ForkJoinPool();

    /**
     * The energy that a Bacteria spend every turn to stay alive.
     */
    public static final Energy ENERGY_FOR_LIVING = new EnergyImpl(COST_OF_LIVING);

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
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final ExistingFoodManager manager,
            final Position maxPosition, final SpeciesManager speciesManager) {
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
            bacteriaState.get().entrySet().stream()
                    .forEach(entry -> this.bacteriaEnv.insertBacteria(entry.getKey(), entry.getValue()));
        } else {
            this.speciesManager.getSpecies().stream().forEach(specie -> {
                final Gene gene = new GeneImpl();
                IntStream.range(0, BACTERIA_PER_SPECIES)
                        .mapToObj(x -> new PositionImpl(rand.nextInt((int) this.simulationMaxPosition.getX()),
                                rand.nextInt((int) this.simulationMaxPosition.getY())))
                        .forEach(position -> {
                            final GeneticCode genCode = new GeneticCodeImpl(gene, BACTERIA_RADIUS, PERCEPTION_RADIUS);
                            final Bacteria bacteria = new BacteriaImpl(bacteriaCounter, specie, genCode,
                                    SimulatorEnvironment.INITIAL_ENERGY);
                            bacteriaCounter++;
                            this.bacteriaEnv.insertBacteria(position, bacteria);
                        });
            });
        }
    }

    private void updateAliveBacteria() {
        this.bacteriaEnv.updateOccupiedPositions();
        final Optional<Double> maxFoodRadius = this.manager.getExistingFoodsSet().stream().map(food -> food.getRadius())
                .max((r1, r2) -> Double.compare(r1, r2));

        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        final List<Position> positions = this.bacteriaEnv.activePosition().stream().collect(Collectors.toList());

        commonPool.invoke(new ActionManager(positions, bacteriaEnv, foodsState, simulationMaxPosition,
                maxFoodRadius, actionPerformer));
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteriaEnv.entrySet().stream()
                .filter(entry -> entry.getValue().isDead()).peek(entry -> {
                    try {
                        this.foodEnv.addFood(entry.getValue().getInternalFood(this.factory), entry.getKey());
                    } catch (PositionAlreadyOccupiedException e) {
                        // Food collided with other food nearby, just don't add
                        Logger.getInstance().info("BacteriaManager", "Bacteria died on Food");
                    }
                }).map(entry -> entry.getKey()).collect(Collectors.toSet());
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
