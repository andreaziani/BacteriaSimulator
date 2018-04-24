package model.simulator;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.mutable.MutableInt;

import model.Direction;
import model.bacteria.Bacteria;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.simulator.task.EatFoodTask;
import model.simulator.task.MoveBacteriaTask;
import model.simulator.task.ReplicateBacteriaTask;
import model.simulator.task.SolelyTask;
import model.simulator.task.Task;
import model.state.Position;

/**
 * Implementation of {@link ActionPerformer}, this class is responsible for
 * creating the task command for each action.
 *
 */
public final class ActionPerformerTask implements ActionPerformer {
    private static final int SINGLE_MUTEX_INDEX = 0;
    private static final int REPLICATE_INTERVAL = 20;
    private static final int MINIMUM_INTERVAL = 10;
    private final BacteriaEnvironment bactEnv;
    private final FoodEnvironment foodEnv;
    private final Position maxPosition;
    private final CopyFactory geneFactory = new CopyFactoryImpl();
    private final Random rand = new Random();

    private final Map<Bacteria, MutableInt> replicateCounter = new ConcurrentHashMap<>();

    private final TaskWithMutex bacteriaTasks;
    private final TaskWithMutex foodTasks;

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
    public ActionPerformerTask(final BacteriaEnvironment bactEnv, final FoodEnvironment foodEnv,
            final Position maxPosition) {
        this.bactEnv = bactEnv;
        this.foodEnv = foodEnv;
        this.maxPosition = maxPosition;
        this.bacteriaTasks = new TaskWithMutex(this.bactEnv.getNumberOfQuadrants());
        this.foodTasks = new TaskWithMutex(1);
    }

    @Override
    public void move(final Position position, final Bacteria bacterium, final Direction direction, final double distance, final boolean isSafe) {
        final int mutexIndex = isSafe ? this.bactEnv.getQuadrant(position) : 0;
        try {
            this.updateStatus(bacterium);
            final Task moveTask = new MoveBacteriaTask(position, bacterium, bactEnv, maxPosition, direction, distance);
            this.bacteriaTasks.perform(mutexIndex, moveTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eat(final Position position, final Bacteria bacterium, final Optional<Position> foodPosition) {
        final int mutexIndex = SINGLE_MUTEX_INDEX;
        try {
            this.updateStatus(bacterium);
            final Task eatTask = new EatFoodTask(position, bacterium, foodEnv, foodPosition);
            this.foodTasks.perform(mutexIndex, eatTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void replicate(final Position position, final Bacteria bacterium, final boolean isSafe) {
        final int mutexIndex = isSafe ? this.bactEnv.getQuadrant(position) : SINGLE_MUTEX_INDEX;
        try {
            this.updateStatus(bacterium);
            final Task replicateTask = new ReplicateBacteriaTask(position, bacterium, bactEnv, maxPosition,
                    geneFactory);
            this.bacteriaTasks.perform(mutexIndex, replicateTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doNothing(final Position position, final Bacteria bacterium) {
        this.updateStatus(bacterium);
        final Task nothingTask = new SolelyTask(position, bacterium);
        nothingTask.execute();
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
}
