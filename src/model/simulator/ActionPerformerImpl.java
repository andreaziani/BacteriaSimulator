package model.simulator;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import model.Direction;
import model.Energy;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.food.Food;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.geneticcode.GeneticCode;
import model.state.Position;

/**
 * Implementation of ActionPerformer interface.
 *
 */
public class ActionPerformerImpl implements ActionPerformer {
    private static final int REPLICATE_INTERVAL = 20;
    private static final int MINIMUM_INTERVAL = 10;
    private final CopyFactory geneFactory = new CopyFactoryImpl();
    private final BacteriaEnvironment bactEnv;
    private final FoodEnvironment foodEnv;
    private final Position simMaxPosition;
    private final Map<Bacteria, MutableInt> replicateCounter = new ConcurrentHashMap<>();
    private final Random rand = new Random();

    private final Mutex bacteriaEnvMutex = new Mutex();
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

    @Override
    public void move(final Position bacteriaPos, final Bacteria bacteria, final Direction moveDirection,
            final double moveDistance) {
        try {
            this.bacteriaEnvMutex.acquire();
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
                this.bacteriaEnvMutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eat(final Position bacteriaPos, final Bacteria bacteria, final Optional<Position> foodPosition) {
        try {
            this.foodEnvMutex.acquire();
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
                this.foodEnvMutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void replicate(final Position bacteriaPos, final Bacteria bacteria) {
        try {
            this.bacteriaEnvMutex.acquire();
            this.updateStatus(bacteria);
            try {
                if (!bacteria.isReplicating()) {
                    bacteria.startReplicating();
                    final double bacteriaRadius = bacteria.getRadius();
                    final Optional<Position> freePosition = EnvironmentUtil
                            .circularPositionStream((int) Math.ceil(bacteriaRadius * 2), bacteriaPos,
                                    this.simMaxPosition)
                            .filter(position -> EnvironmentUtil.causeCollision(position, bacteriaRadius, simMaxPosition,
                                    pos -> this.bactEnv.isPositionOccupied(pos)))
                            .findAny();

                    if (freePosition.isPresent()) {
                        final GeneticCode clonedGenCode = this.geneFactory.copyGene(bacteria.getGeneticCode());
                        final Energy halfEnergy = bacteria.getEnergy().multiply(0.5);
                        bacteria.spendEnergy(halfEnergy);
                        final int nextBacteriaID = this.bactEnv.getNumberOfBacteria();
                        final Bacteria newBacteria = new BacteriaImpl(nextBacteriaID, bacteria.getSpecies(),
                                clonedGenCode, halfEnergy);
                        this.bactEnv.insertBacteria(freePosition.get(), newBacteria);
                    }
                }
            } finally {
                this.bacteriaEnvMutex.release();
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
