package model.simulator;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import model.Direction;
import model.Energy;
import model.EnergyImpl;
import model.Position;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalAction;
import model.action.DirectionalActionImpl;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import model.geneticcode.CopyFactory;
import model.geneticcode.CopyFactoryImpl;
import model.geneticcode.GeneticCode;
import utils.EnvUtil;


/**
 * Implementation of BacteriaManager.
 *
 */
public class BacteriaManagerImpl implements BacteriaManager {
    private final Energy energyForLiving;
    private final FoodFactory factory = new FoodFactoryImpl();
    private final FoodEnvironment foodEnv;
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    private final ActionPerformer actionPerf = new ActionPerformer();
    private final CopyFactory geneFactory = new CopyFactoryImpl();

    /**
     * Constructor.
     * 
     * @param foodEnv
     *            used to update food environment according to bacteria actions
     * @param costOfLiving
     *            amount of energy that a Bacteria spend just to stay alive
     */
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final double costOfLiving) {
        this.foodEnv = foodEnv;
        this.energyForLiving = new EnergyImpl(costOfLiving);
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos,
            final Map<Position, Food> foodsState) {
        final double radius = this.bacteria.get(bacteriaPos).getPerceptionRadius();
        final int start = (int) -Math.ceil(radius);
        final int end = (int) Math.ceil(radius);
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvUtil.positionStream(start, end, bacteriaPos)
                .map(pos -> Pair.of(pos, EnvUtil.distance(pos, bacteriaPos)))
                .filter(posDistPair -> posDistPair.getRight() <= radius)
                .filter(posDistPair -> foodsState.containsKey(posDistPair.getLeft())).map(posDistPair -> {
                    final double angle = EnvUtil.angle(bacteriaPos, posDistPair.getLeft());
                    final Direction dir = EnvUtil.angleToDir(angle);
                    return Pair.of(dir, posDistPair.getRight());
                })
                .filter(dirDistPair -> !distsToFood.containsKey(dirDistPair.getLeft())
                        || dirDistPair.getRight() < distsToFood.get(dirDistPair.getLeft()))
                .forEach(dirDist -> distsToFood.put(dirDist.getLeft(), dirDist.getRight()));
        return distsToFood;
    }

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final Optional<Food> foodInPosition = foodsState.containsKey(bacteriaPos)
                ? Optional.of(foodsState.get(bacteriaPos))
                : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
        actionPerf.setStatus(bacteriaPos, bacteria);
        final Action action = bacteria.getAction();
        final ActionType actionType = action.getType();

        switch (actionType) {
        case MOVE:
            final DirectionalAction moveAction = (DirectionalActionImpl) action;
            actionPerf.move(moveAction.getDirection());
            break;
        case EAT:
            actionPerf.eat();
            break;
        case REPLICATE:
            actionPerf.replicate();
            break;
        default:
            actionPerf.doNothing();
            break;
        }
        bacteria.spendEnergy(bacteria.getActionCost(action));
    }

    private void costOfLiving(final Bacteria bacteria) {
        bacteria.spendEnergy(this.energyForLiving);
    }

    private void updateLivingBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        this.bacteria.entrySet().stream()
                .peek(e -> e.getValue().setPerception(this.createPerception(e.getKey(), foodsState)))
                .peek(e -> this.performAction(e.getKey(), e.getValue())).forEach(e -> this.costOfLiving(e.getValue()));
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteria.entrySet().stream().filter(e -> e.getValue().isDead())
                .peek(e -> this.foodEnv.addFood(e.getValue().getInternalFood(this.factory), e.getKey()))
                .map(e -> e.getKey()).collect(Collectors.toSet());
        this.bacteria.keySet().removeAll(toBeRemoved);
    }

    /**
     * Update Bacteria every turn.
     */
    @Override
    public void updateBacteria() {
        this.updateDeadBacteria();
        this.updateLivingBacteria();
    }

    /**
     * Implementation of getBacteriaState.
     */
    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return Collections.unmodifiableMap(this.bacteria);
    }

    /**
     * Inner class whose sole task is to perform Bacteria's actions.
     */
    private class ActionPerformer {
        private Position bacteriaPos;
        private Bacteria bacteria;

        private ActionPerformer() {
        }

        private void setStatus(final Position bacteriaPos, final Bacteria bacteria) {
            this.bacteriaPos = bacteriaPos;
            this.bacteria = bacteria;
        }

        private void move(final Direction moveDirection) {
            final double movement = this.bacteria.getSpeed() * EnvUtil.UNIT_OF_TIME;
            final int start = (int) -Math.ceil(movement);
            final int end = (int) Math.ceil(movement);
            final Optional<Position> newPosition = EnvUtil.positionStream(start, end, bacteriaPos)
                                                          .filter(position -> EnvUtil.angleToDir(EnvUtil.angle(bacteriaPos, position)).equals(moveDirection))
                                                          .findAny();
            if (newPosition.isPresent()) {
                BacteriaManagerImpl.this.bacteria.remove(this.bacteriaPos);
                BacteriaManagerImpl.this.bacteria.put(newPosition.get(), this.bacteria);
            }
        }

        private void eat() {
            final Optional<Food> foodInPosition = BacteriaManagerImpl.this.foodEnv.getFoodsState().containsKey(
                    this.bacteriaPos) ? Optional.of(foodEnv.getFoodsState().get(this.bacteriaPos)) : Optional.empty();
            if (foodInPosition.isPresent()) {
                this.bacteria.addFood(foodInPosition.get());
                BacteriaManagerImpl.this.foodEnv.removeFood(foodInPosition.get(), this.bacteriaPos);
            }
        }

        private void replicate() {
            final double bacteriaRadius = this.bacteria.getRadius();
            final int start = (int) -Math.ceil(bacteriaRadius * 2);
            final int end = (int) Math.ceil(bacteriaRadius * 2);

            final Optional<Position> freePosition = EnvUtil.positionStream(start, end, this.bacteriaPos)
                    .filter(position -> !BacteriaManagerImpl.this.bacteria.containsKey(position))
                    .filter(position -> !EnvUtil.isCollision(
                            Pair.of(position, BacteriaManagerImpl.this.bacteria.get(position)),
                            Pair.of(this.bacteriaPos, this.bacteria)))
                    .findAny();

            if (freePosition.isPresent()) {
                final GeneticCode clonedGenCode = BacteriaManagerImpl.this.geneFactory.copyGene(this.bacteria.getGeneticCode());
                final Energy bactEnergy = this.bacteria.getEnergy();
                final Energy halfEnergy = bactEnergy.multiply(0.5);
                this.bacteria.spendEnergy(halfEnergy);
                final Bacteria newBacteria = new BacteriaImpl(this.bacteria.getSpecies(), clonedGenCode, halfEnergy);
                BacteriaManagerImpl.this.bacteria.put(freePosition.get(), newBacteria);
            }
        }

        private void doNothing() {
            //
        }
    }
}
