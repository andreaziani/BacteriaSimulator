package model.simulator;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import model.Direction;
import model.Energy;
import model.Position;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.geneticcode.GeneticCode;
import utils.EnvironmentUtil;

/**
 * Implementation of ActionPerformer interface.
 *
 */
public class ActionPerformerImpl implements ActionPerformer {
    private final CopyFactory geneFactory = new CopyFactoryImpl();
    private final BacteriaEnvironment bactEnv;
    private final FoodEnvironment foodEnv;
    private final Position simulationMaxPosition;
    private Position currentPosition;
    private Bacteria bacterium;

    /**
     * Constructor of ActionPerformerImpl.
     * @param bactEnv the environment representing bacteria
     * @param foodEnv the environment representing foods
     */
    public ActionPerformerImpl(final BacteriaEnvironment bactEnv, final FoodEnvironment foodEnv, final Position maxPosition) {
        this.bactEnv = bactEnv;
        this.foodEnv = foodEnv;
        this.simulationMaxPosition = maxPosition;
    }

    @Override
    public void setStatus(final Position bacteriumPos, final Bacteria bacterium) {
        this.currentPosition = bacteriumPos;
        this.bacterium = bacterium;
    }

    @Override
    public void move(final Direction moveDirection) {
        final double movement = this.bacterium.getSpeed() * EnvironmentUtil.UNIT_OF_TIME;
        final Pair<Position, Bacteria> dataPair = Pair.of(this.currentPosition, this.bacterium);
        final int distance = (int) Math.ceil(movement);
        final Optional<Position> newPosition = EnvironmentUtil.positionStream(distance, currentPosition, this.simulationMaxPosition)
                .filter(position -> EnvironmentUtil.angleToDir(EnvironmentUtil.angle(currentPosition, position))
                        .equals(moveDirection))
                .filter(position -> !EnvironmentUtil.causeCollision(dataPair, position, this.bactEnv.getBacteriaState()))
                .filter(position -> !this.bactEnv.containBacteriaInPosition(position))
                .findAny();
        if (newPosition.isPresent()) {
            this.bactEnv.changeBacteriaPosition(this.currentPosition, newPosition.get());
        }
    }

    @Override
    public void eat(final Optional<Position> foodPosition) {
        final Optional<Food> foodInPosition;
        if (foodPosition.isPresent() && this.foodEnv.getFoodsState().containsKey(foodPosition.get())) {
            foodInPosition =  Optional.of(foodEnv.getFoodsState().get(foodPosition.get()));
        } else {
            foodInPosition = Optional.empty();
        }

        if (foodInPosition.isPresent()) {
            this.bacterium.addFood(foodInPosition.get());
            this.foodEnv.removeFood(foodInPosition.get(), foodPosition.get());
        }
    }

    @Override
    public boolean replicate(final int bacteriaCounter) {
        final double bacteriaRadius = this.bacterium.getRadius();
        final int start = (int) -Math.ceil(bacteriaRadius * 2);
        final int end = (int) Math.ceil(bacteriaRadius * 2);

        final Optional<Position> freePosition = EnvironmentUtil.positionStream(start, end, this.currentPosition, this.simulationMaxPosition)
                .filter(position -> !this.bactEnv.containBacteriaInPosition(position))  // exclude position already occupied
                .filter(position -> !EnvironmentUtil.isCollision(
                        Pair.of(position, this.bacterium),
                        Pair.of(this.currentPosition, this.bacterium)))    // exclude position that would cause a collision
                .findAny();

        if (freePosition.isPresent()) {
            final GeneticCode clonedGenCode = this.geneFactory.copyGene(this.bacterium.getGeneticCode());
            final Energy halfEnergy = this.bacterium.getEnergy().multiply(0.5);
            this.bacterium.spendEnergy(halfEnergy);
            final Bacteria newBacteria = new BacteriaImpl(bacteriaCounter, this.bacterium.getSpecies(), clonedGenCode, halfEnergy);
            this.bactEnv.insertBacteria(freePosition.get(), newBacteria);
            return true;
        }
        return false;
    }

    @Override
    public void doNothing() {
        // TODO Auto-generated method stub
    }
}
