package model.simulator;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import model.Direction;
import model.Energy;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.species.Species;
import model.food.Food;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.geneticcode.GeneticCode;
import model.state.Position;

/**
 * Implementation of ActionPerformer interface.
 *
 */
public final class ActionPerformerImpl implements ActionPerformer {
    private static final int SINGLE_MUTEX_INDEX = 0;
    private static final int REPLICATE_INTERVAL = 20;
    private static final int MINIMUM_INTERVAL = 10;
    private final CopyFactory geneFactory = new CopyFactoryImpl();
    private final BacteriaEnvironment bactEnv;
    private final FoodEnvironment foodEnv;
    private final Position simMaxPosition;
    private final Map<Bacteria, MutableInt> replicateCounter = new ConcurrentHashMap<>();
    private final Random rand = new Random();

    private final List<Mutex> quadrantsMutex = new ArrayList<>();
    private final Mutex foodEnvMutex = new Mutex();

    /**
     * Constructor of ActionPerformerImpl.
     * 
     * @param bactEnv
     *            the environment representing bacteria
     * @param foodEnv
     *            the environment representing foods
     * @param maxPosition
     *            the environment maxPosition
     */
    public ActionPerformerImpl(final BacteriaEnvironment bactEnv, final FoodEnvironment foodEnv,
            final Position maxPosition) {
        this.bactEnv = bactEnv;
        this.foodEnv = foodEnv;
        this.simMaxPosition = maxPosition;
        IntStream.range(0, this.bactEnv.getNumberOfQuadrants()).forEach(x -> this.quadrantsMutex.add(new Mutex()));
    }

    private int nextTurn() {
        return rand.nextInt(REPLICATE_INTERVAL) + MINIMUM_INTERVAL;
    }

    private void updateStatus(final Bacteria bacteria) {
        if (!replicateCounter.containsKey(bacteria)) {
            replicateCounter.put(bacteria, new MutableInt(nextTurn()));
        }
        if (replicateCounter.get(bacteria).decrementAndGet() == 0) {
            replicateCounter.get(bacteria).setValue(nextTurn());
            bacteria.stopReplicating();
        }
    }

    private void acquireEnvMutex(final int index) throws InterruptedException {
        this.quadrantsMutex.get(index).acquire();
    }

    private void releaseEnvMutex(final int index) {
        this.quadrantsMutex.get(index).release();
    }

    private Bacteria cloneBacteria(final Bacteria bacteria) {
        final int bacteriaID = this.bactEnv.getNumberOfBacteria();
        final Species species = bacteria.getSpecies();
        final GeneticCode geneticCode = this.geneFactory.copyGene(bacteria.getGeneticCode());
        final Energy halfEnergy = bacteria.getEnergy().multiply(0.5);
        bacteria.spendEnergy(halfEnergy);

        return new BacteriaImpl(bacteriaID, species, geneticCode, halfEnergy);
    }

    @Override
    public void move(final Position bacteriaPos, final Bacteria bacteria, final Direction moveDirection,
            final double moveDistance, final boolean isSafe) {
        try {
            if (isSafe) {
                this.acquireEnvMutex(this.bactEnv.getQuadrant(bacteriaPos));
            } else {
                this.acquireEnvMutex(SINGLE_MUTEX_INDEX);
            }
            // Logger.getInstance().info("MOVE" + this.bactEnv.getQuad(bacteriaPos),
            // "THREAD" + Thread.currentThread().getId() + " IN");
            this.updateStatus(bacteria);
            try {
                final double maximumDistance = bacteria.getSpeed() * EnvironmentUtil.UNIT_OF_TIME;
                final double distance = Math.min(moveDistance, maximumDistance);
                this.bactEnv.clearPosition(bacteriaPos, bacteria);

                final Optional<Position> newPosition = EnvironmentUtil
                        .circularPositionStream((int) Math.ceil(distance), bacteriaPos, this.simMaxPosition)
                        .filter(position -> EnvironmentUtil
                                .angleToDir(EnvironmentUtil.angle(bacteriaPos, position)) == moveDirection)
                        .filter(position -> EnvironmentUtil.causeCollision(position,
                                (int) Math.ceil(bacteria.getRadius()), simMaxPosition,
                                pos -> this.bactEnv.isPositionOccupied(pos)))
                        .max((p1, p2) -> Double.compare(EnvironmentUtil.distance(bacteriaPos, p1),
                                EnvironmentUtil.distance(bacteriaPos, p2)));

                if (newPosition.isPresent()) {
                    this.bactEnv.changeBacteriaPosition(bacteriaPos, newPosition.get());
                    this.bactEnv.markPosition(newPosition.get(), bacteria);
                } else {
                    this.bactEnv.markPosition(bacteriaPos, bacteria);
                }
            } finally {
                // Logger.getInstance().info("MOVE" + this.bactEnv.getQuad(bacteriaPos),
                // "THREAD" + Thread.currentThread().getId() + " OUT");
                if (isSafe) {
                    this.releaseEnvMutex(this.bactEnv.getQuadrant(bacteriaPos));
                } else {
                    this.releaseEnvMutex(SINGLE_MUTEX_INDEX);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eat(final Position bacteriaPos, final Bacteria bacteria, final Optional<Position> foodPosition) {
        try {
            this.foodEnvMutex.acquire();
            // Logger.getInstance().info("EAT", "THREAD" + Thread.currentThread().getId() +
            // " OUT");
            this.updateStatus(bacteria);
            try {
                Optional<Food> foodInPosition = Optional.empty();
                if (foodPosition.isPresent() && this.foodEnv.getFoodsState().containsKey(foodPosition.get())) {
                    foodInPosition = Optional.of(this.foodEnv.getFoodsState().get(foodPosition.get()));
                }

                if (foodInPosition.isPresent()) {
                    bacteria.addFood(foodInPosition.get());
                    this.foodEnv.removeFood(foodInPosition.get(), foodPosition.get());
                }
            } finally {
                // Logger.getInstance().info("EAT", "THREAD" + Thread.currentThread().getId() +
                // " IN");
                this.foodEnvMutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void replicate(final Position bacteriaPos, final Bacteria bacteria, final boolean isSafe) {
        try {
            if (isSafe) {
                this.acquireEnvMutex(this.bactEnv.getQuadrant(bacteriaPos));
            } else {
                this.acquireEnvMutex(SINGLE_MUTEX_INDEX);
            }
            // Logger.getInstance().info("REPLIC" + this.bactEnv.getQuad(bacteriaPos),
            // "THREAD" + Thread.currentThread().getId() + " IN");
            this.updateStatus(bacteria);
            try {
                if (!bacteria.isReplicating()) {
                    bacteria.startReplicating();
                    final double bacteriaRadius = bacteria.getRadius();

                    final List<Position> positions = EnvironmentUtil
                            .circularPositionStream((int) Math.ceil(bacteriaRadius * 2), bacteriaPos,
                                    this.simMaxPosition)
                            .filter(position -> EnvironmentUtil.causeCollision(position, bacteriaRadius, simMaxPosition,
                                    pos -> this.bactEnv.isPositionOccupied(pos)))
                            .collect(Collectors.toList());

                    if (!positions.isEmpty()) {
                        final Bacteria newBacteria = cloneBacteria(bacteria);
                        final Position freePosition = positions
                                .get(ThreadLocalRandom.current().nextInt(positions.size()));
                        this.bactEnv.insertBacteria(freePosition, newBacteria);
                    }
                }
            } finally {
                // Logger.getInstance().info("REPLIC" + this.bactEnv.getQuad(bacteriaPos),
                // "THREAD" + Thread.currentThread().getId() + " OUT");
                if (isSafe) {
                    this.releaseEnvMutex(this.bactEnv.getQuadrant(bacteriaPos));
                } else {
                    this.releaseEnvMutex(SINGLE_MUTEX_INDEX);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doNothing(final Position bacteriaPos, final Bacteria bacteria) {
        this.updateStatus(bacteria);
    }
}
