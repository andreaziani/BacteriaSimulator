package model.simulator;

import java.util.Optional;

import model.Direction;
import model.Energy;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.geneticcode.GeneticCode;
import model.state.Position;
import utils.EnvironmentUtil;
import utils.Mutex;

/**
 * Implementation of ActionPerformer interface.
 *
 */
public class ActionPerformerImpl implements ActionPerformer {
    private final CopyFactory geneFactory = new CopyFactoryImpl();
    private final BacteriaEnvironment bactEnv;
    private final FoodEnvironment foodEnv;
    private final Position simulationMaxPosition;

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
        this.simulationMaxPosition = maxPosition;
    }

    @Override
    public void move(final Position bacteriaPos, final Bacteria bacteria, final Direction moveDirection,
            final double moveDistance) {
        try {
            this.bacteriaEnvMutex.acquire();
            try {
                final double maximumDistance = bacteria.getSpeed() * EnvironmentUtil.UNIT_OF_TIME;
                final double distance = Math.min(moveDistance, maximumDistance);
                this.bactEnv.clearPosition(bacteriaPos, bacteria);

                final Optional<Position> newPosition = EnvironmentUtil
                        .positionStream((int) Math.ceil(distance), bacteriaPos, this.simulationMaxPosition)
                        .filter(position -> EnvironmentUtil.angleToDir(EnvironmentUtil.angle(bacteriaPos, position))
                                .equals(moveDirection))
                        .filter(position -> {
                            return !EnvironmentUtil
                                    .positionStream((int) Math.ceil(bacteria.getRadius()), position,
                                            this.simulationMaxPosition)
                                    .anyMatch(pos -> this.bactEnv.isPositionOccupied(pos));
                        }).max((p1, p2) -> Double.compare(EnvironmentUtil.distance(bacteriaPos, p1),
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
            try {
                Optional<Food> foodInPosition = Optional.empty();
                if (foodPosition.isPresent() && this.foodEnv.getFoodsState().containsKey(foodPosition.get())) {
                    foodInPosition = Optional.of(this.foodEnv.getFoodsState().get(foodPosition.get()));
                }

                if (foodInPosition.isPresent()) {
                    System.err.println("BActeria eat");
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
    public void replicate(final Position bacteriaPos, final Bacteria bacteria, final int bacteriaCounter) {
        try {
            this.bacteriaEnvMutex.acquire();
            try {
                final double bacteriaRadius = bacteria.getRadius();

                final Optional<Position> freePosition = EnvironmentUtil
                        .positionStream((int) Math.ceil(bacteriaRadius * 2), bacteriaPos, this.simulationMaxPosition)
                        .filter(position -> {
                            return !EnvironmentUtil
                                    .positionStream((int) Math.ceil(bacteria.getRadius()), position, this.simulationMaxPosition)
                                    .anyMatch(pos -> this.bactEnv.isPositionOccupied(pos));
                        }).findAny();

                if (freePosition.isPresent()) {
                    final GeneticCode clonedGenCode = this.geneFactory.copyGene(bacteria.getGeneticCode());
                    final Energy halfEnergy = bacteria.getEnergy().multiply(0.5);
                    bacteria.spendEnergy(halfEnergy);
                    final Bacteria newBacteria = new BacteriaImpl(bacteriaCounter, bacteria.getSpecies(), clonedGenCode,
                            halfEnergy);
                    this.bactEnv.insertBacteria(freePosition.get(), newBacteria);
                }
            } finally {
                this.bacteriaEnvMutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doNothing(final Position bacteriaPos, final Bacteria bacteriat) {
    }
}
