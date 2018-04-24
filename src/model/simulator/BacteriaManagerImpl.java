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
public final class BacteriaManagerImpl implements BacteriaManager {
    private static final double COST_OF_LIVING = 1.5;
    private static final int TOTAL_BACTERIA = 80;
    private static final double BACTERIA_RADIUS = 5.0;
    private static final double PERCEPTION_RADIUS = 40.0;
    private final Position maxPosition;
    private final FoodEnvironment foodEnv;
    private final ExistingFoodManager manager;
    private final FoodFactory factory = new FoodFactoryImpl();
    private final SpeciesManager speciesManager;
    private final BacteriaEnvironment bacteriaEnv;
    private final ActionPerformer actionPerformer;
    private final Random rand = new Random();

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
     *            the food manager containing informations about the food that have
     *            already been created
     * @param maxPosition
     *            contains information about the maximum position in the simulation
     * @param speciesManager
     *            species manager containing information about existing species
     */
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final ExistingFoodManager manager,
            final Position maxPosition, final SpeciesManager speciesManager) {
        this.foodEnv = foodEnv;
        this.manager = manager;
        this.maxPosition = maxPosition;
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
            final int bacteriaPerSpecies = (int) Math
                    .ceil((double) TOTAL_BACTERIA / this.speciesManager.getSpecies().size());
            this.speciesManager.getSpecies().stream().forEach(specie -> {
                final Gene gene = new GeneImpl();
                IntStream.range(0, bacteriaPerSpecies)
                        .mapToObj(x -> new PositionImpl(rand.nextInt((int) this.maxPosition.getX()),
                                rand.nextInt((int) this.maxPosition.getY())))
                        .forEach(position -> {
                            final GeneticCode genCode = new GeneticCodeImpl(gene, BACTERIA_RADIUS, PERCEPTION_RADIUS);
                            final int nextBacteriaID = this.bacteriaEnv.getNumberOfBacteria();
                            final Bacteria bacteria = new BacteriaImpl(nextBacteriaID, specie, genCode,
                                    SimulatorEnvironment.INITIAL_ENERGY);
                            this.bacteriaEnv.insertBacteria(position, bacteria);
                        });
            });
        }
    }

    private void updateAliveBacteria() {
        this.bacteriaEnv.updateOccupiedPositions();

        final Optional<Double> maxFoodRadius = this.manager.getExistingFoods().stream().map(food -> food.getRadius())
                .max((r1, r2) -> Double.compare(r1, r2));

        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();

        final List<Position> safe = this.bacteriaEnv.activePosition().stream()
                .filter(pos -> this.bacteriaEnv.isSafe(pos)).collect(Collectors.toList());
        final List<Position> unSafe = this.bacteriaEnv.activePosition().stream()
                .filter(pos -> !this.bacteriaEnv.isSafe(pos)).collect(Collectors.toList());

        commonPool.invoke(new ActionManager(safe.stream(), safe.size(), bacteriaEnv, foodsState, maxFoodRadius,
                actionPerformer, true));

        commonPool.invoke(new ActionManager(unSafe.stream(), unSafe.size(), bacteriaEnv, foodsState, maxFoodRadius,
                actionPerformer, false));
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteriaEnv.entrySet().stream()
                .filter(entry -> entry.getValue().isDead()).peek(entry -> {
                    try {
                        this.foodEnv.addFood(entry.getValue().getInternalFood(this.factory), entry.getKey());
                    } catch (PositionAlreadyOccupiedException e) {
                        // Food collided with other food nearby, just don't add
                        Logger.getInstance().info("Bacteria Manager", "Bacteria died on Food");
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

    @Override
    public List<Bacteria> getAliveBacteria() {
        return this.bacteriaEnv.getBacteriaState().values().stream().filter(bacteria -> !bacteria.isDead())
                .collect(Collectors.toList());
    }
}
