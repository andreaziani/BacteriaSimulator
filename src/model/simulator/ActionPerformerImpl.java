package model.simulator;

import java.util.Optional;

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
     * 
     * @param bactEnv
     *            the environment representing bacteria
     * @param foodEnv
     *            the environment representing foods
     */
    public ActionPerformerImpl(final BacteriaEnvironment bactEnv, final FoodEnvironment foodEnv,
            final Position maxPosition) {
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
        this.bactEnv.clearPosition(this.currentPosition, this.bacterium);

        final Optional<Position> newPosition = EnvironmentUtil
                .positionStream((int) Math.ceil(movement), currentPosition, this.simulationMaxPosition)
                .filter(position -> EnvironmentUtil.angleToDir(EnvironmentUtil.angle(currentPosition, position))
                        .equals(moveDirection))
                .filter(position -> {
                        return !EnvironmentUtil.positionStream((int) Math.ceil(this.bacterium.getRadius()), position,
                                this.simulationMaxPosition).anyMatch(pos -> this.bactEnv.isPositionOccupied(pos));
                })
                .max((p1, p2) -> Double.compare(EnvironmentUtil.distance(currentPosition, p1),
                        EnvironmentUtil.distance(currentPosition, p2)));

        if (newPosition.isPresent()) {
            this.bactEnv.changeBacteriaPosition(this.currentPosition, newPosition.get());
            this.bactEnv.markPosition(newPosition.get(), this.bacterium);
        } else {
            this.bactEnv.markPosition(this.currentPosition, this.bacterium);
        }
    }

    @Override
    public void eat(final Optional<Position> foodPosition) {
        Optional<Food> foodInPosition = Optional.empty();
        if (foodPosition.isPresent() && this.foodEnv.getFoodsState().containsKey(foodPosition.get())) {
            foodInPosition = Optional.of(foodEnv.getFoodsState().get(foodPosition.get()));
        }

        if (foodInPosition.isPresent()) {
            this.bacterium.addFood(foodInPosition.get());
            this.foodEnv.removeFood(foodInPosition.get(), foodPosition.get());
        }
    }

    @Override
    public boolean replicate(final int bacteriaCounter) {
        final double bacteriaRadius = this.bacterium.getRadius();

        final Optional<Position> freePosition = EnvironmentUtil
                .positionStream((int) Math.ceil(bacteriaRadius * 2), this.currentPosition, this.simulationMaxPosition)
                .filter(position -> {
                        return !EnvironmentUtil.positionStream((int) Math.ceil(this.bacterium.getRadius()), position,
                                this.simulationMaxPosition).anyMatch(pos -> this.bactEnv.isPositionOccupied(pos));
                })
                .findAny();

        if (freePosition.isPresent()) {
            final GeneticCode clonedGenCode = this.geneFactory.copyGene(this.bacterium.getGeneticCode());
            final Energy halfEnergy = this.bacterium.getEnergy().multiply(0.5);
            this.bacterium.spendEnergy(halfEnergy);
            final Bacteria newBacteria = new BacteriaImpl(bacteriaCounter, this.bacterium.getSpecies(), clonedGenCode,
                    halfEnergy);
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
