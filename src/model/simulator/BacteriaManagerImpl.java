package model.simulator;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.Direction;
import model.EnergyImpl;
import model.GeneticCode;
import model.GeneticCodeImpl;
import model.Position;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import utils.EnvUtil;

import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Implementation of BacteriaManager.
 *
 */
public class BacteriaManagerImpl implements BacteriaManager {
    private final double ENV_COST_OF_LIVING;
    private final FoodFactory factory = new FoodFactoryImpl();
    private final FoodEnvironment foodEnv;
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    private final ActionPerformer actionPerf = new ActionPerformer();

    /**
     * Constructor.
     * @param foodEnv used to update food env according to bacteria actions
     * @param COST_OF_LIVING amount of energy that a Bacteria spend just to stay alive
     */
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final double COST_OF_LIVING) {
        this.foodEnv = foodEnv;
        this.ENV_COST_OF_LIVING = COST_OF_LIVING;
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final double radius = this.bacteria.get(bacteriaPos).getPerceptionRadius();
        final int start = (int) -Math.ceil(radius);
        final int end = (int) Math.ceil(radius);
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvUtil.positionStream(start, end, bacteriaPos).map(pos -> ImmutablePair.of(pos, EnvUtil.distance(pos, bacteriaPos)))
                                                           .filter(posDistPair -> posDistPair.getRight() <= radius)
                                                           .filter(posDistPair -> foodsState.containsKey(posDistPair.getLeft()))
                                                           .map(posDistPair -> ImmutablePair.of(EnvUtil.angle(bacteriaPos, posDistPair.getLeft()), posDistPair.getRight()))
                                                           .map(angleDistPair -> ImmutablePair.of(EnvUtil.angleToDir(angleDistPair.getLeft()), angleDistPair.getRight()))
                                                           .filter(dirDistPair -> !distsToFood.containsKey(dirDistPair.getLeft()) 
                                                                                  || dirDistPair.getRight() < distsToFood.get(dirDistPair.getLeft()))
                                                           .forEach(dirDist -> distsToFood.put(dirDist.getLeft(), dirDist.getRight()));
        return distsToFood;
    }

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final Optional<Food> foodInPosition = foodsState.containsKey(bacteriaPos) ? Optional.of(foodsState.get(bacteriaPos)) : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
        actionPerf.setStatus(bacteriaPos, bacteria);
        switch (bacteria.getAction().getType()) {
        case MOVE: actionPerf.move();
            break;
        case EAT: actionPerf.eat();
            break;
        case REPLICATE: actionPerf.replicate();
            break;
        default: actionPerf.doNothing();
            break;
        }
    }

    private void costOfLiving(final Bacteria bacteria) {
        bacteria.spendEnergy(new EnergyImpl(this.ENV_COST_OF_LIVING));
    }

    private void updateLivingBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        this.bacteria.entrySet().stream()
                                .peek(e -> e.getValue().setPerception(this.createPerception(e.getKey(), foodsState)))
                                .peek(e -> this.performAction(e.getKey(), e.getValue()))
                                .forEach(e -> this.costOfLiving(e.getValue()));
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteria.entrySet().stream()
                                                            .filter(e -> e.getValue().isDead())
                                                            .peek(e -> this.foodEnv.addFood(e.getValue().getInternalFood(this.factory), e.getKey()))
                                                            .map(e -> e.getKey())
                                                            .collect(Collectors.toSet());
        this.bacteria.keySet().removeAll(toBeRemoved);
    }

    @Override
    public void updateBacteria() {
        this.updateLivingBacteria();
        this.updateDeadBacteria();
    }

    /**
     * Inner class whose sole task is to perform Baacteria's actions.
     */
    private class ActionPerformer {
        private Position bacteriaPos;
        private Bacteria bacteria;

        private void setStatus(final Position bacteriaPos, final Bacteria bacteria) {
            this.bacteriaPos = bacteriaPos;
            this.bacteria = bacteria;
        }

        private void move() {
            //
        }

        private void eat() {
            //
        }

        private void replicate() {
            final double bacteriaRadius = this.bacteria.getRadius();
            final int start = (int) -Math.ceil(bacteriaRadius * 2);
            final int end = (int) Math.ceil(bacteriaRadius * 2);
            final Optional<Position> freePosition = EnvUtil.positionStream(start, end, this.bacteriaPos)
                                                     .filter(position -> !BacteriaManagerImpl.this.bacteria.containsKey(position))
                                                     .filter(position -> !EnvUtil.isCollision(position, this.bacteriaPos, BacteriaManagerImpl.this.bacteria))
                                                     .findAny();
            if (freePosition.isPresent()) {
                // TODO maybe a .clone() method could be useful
                final GeneticCode oldGenCode = this.bacteria.getGeneticCode();
                // TODO can't clone genetic code in this way (actions and nutrients are missing)
                final GeneticCode newGenCode = new GeneticCodeImpl(oldGenCode.getCode(), null, null, oldGenCode.getSpeed());
                // TODO clone both species and energy
                final Bacteria newBacteria = new BacteriaImpl(this.bacteria.getSpecies(), newGenCode, this.bacteria.getEnergy());
                BacteriaManagerImpl.this.bacteria.put(freePosition.get(), newBacteria);
            }
        }

        private void doNothing() {
            //
        }
    }
}
